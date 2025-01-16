package com.example.springboot;

import com.example.springboot.POJO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.annotation.PostConstruct;
import jakarta.jws.WebService;
import org.apache.cxf.frontend.FaultInfoException;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import java.util.AbstractMap;
import java.util.Map;


@Component
public class HrServiceImpl implements HrService{

    com.fasterxml.jackson.databind.ObjectMapper mapper;


    @PostConstruct
    public void init(){
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            Unirest.setHttpClient(httpclient);
        } catch (Exception e){
            e.printStackTrace();
        }
        mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    }

    @Transactional
    @Override
    public Out fireWorker(String Id){
        Errors errors = new Errors();
        Out out = new Out();

        Map.Entry<String, Integer> error = null;
        int workerId;

        try{
            workerId = Integer.parseInt(Id);
        } catch (NumberFormatException e){
            errors.addError(422, "Id should be integer");
            out.setStatus(422);
            try {
                out.setMsg(mapper.writeValueAsString(errors));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            return out;
        }
//        https://127.0.0.1:8443/worker-0.0.1/api/workers/
        try {
            HttpResponse<String> jsonResponse = Unirest.get("https://127.0.0.1:8443/worker-0.0.1/api/workers/" + workerId)
                    .header("Content-Type", "application/json")
                    .asString();
            String responseBody = jsonResponse.getBody();
            if (jsonResponse.getStatus() == 404){
                errors.addError(503, "Callable service is down");
                out.setStatus(503);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            }
            if (jsonResponse.getStatus() != 200) {
                errors.addError(422, "Worker does not exist");
                out.setStatus(422);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            }
            Worker worker = mapper.readValue(responseBody, Worker.class);
            if (worker.getStatus().equalsIgnoreCase(Status.FIRED.toString())) {
                errors.addError(422, "Worker is already fired");
                out.setStatus(422);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            }
            Worker outw = new Worker();
            outw.setName(worker.getName());
            outw.setSalary(worker.getSalary());
            outw.setCreationDate(worker.getCreationDate());
            outw.setStartDate(worker.getStartDate());
            outw.setEndDate(worker.getEndDate());
            outw.setStatus(Status.FIRED.toString());
            HttpResponse<String> jsonPatchResponse = Unirest.patch("https://127.0.0.1:8443/worker-0.0.1/api/workers/" + workerId)
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(outw))
                    .asString();
            if (jsonPatchResponse.getStatus() == 200) {
                out.setStatus(200);
                out.setMsg("Worker was fired");
                return out;
            } else {
                String msg = jsonPatchResponse.getBody();
                out.setStatus(jsonResponse.getStatus());
                out.setMsg(mapper.writeValueAsString(msg));
                return out;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errors.addError(503, "Callable service is down");
            try {
                out.setStatus(503);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Transactional
    public Out hireWorker(String salaryStr, String startDate, HireWorkerDTO person){
        Errors errors = new Errors();
        Out out = new Out();
        int salary;

        try{
            salary = Integer.parseInt(salaryStr);
        } catch (NumberFormatException e){
            errors.addError(422, "Salary should be integer");
            try {
                out.setStatus(422);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }


        Worker worker = new Worker();
        Person newPerson = new Person();
        newPerson.setBirthday(person.getBirthday());
        newPerson.setNationality(person.getNationality());
        newPerson.setEyeColor(person.getEyeColor());
        newPerson.setHairColor(person.getHairColor());
        if (person.getLocation() != null) {
            try {
                Location location = new Location();
                location.setName(person.getLocation().getName());
                location.setX(Double.valueOf(person.getLocation().getX()));
                location.setY(Double.valueOf(person.getLocation().getY()));
                location.setZ(Float.valueOf(person.getLocation().getZ()));
                newPerson.setLocation(location);
            } catch (NumberFormatException e){
                errors.addError(422, "Location x,y,z should be double, double, float");
                out.setStatus(422);
                try {
                    out.setMsg(mapper.writeValueAsString(errors));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                return out;
            }
        } else {
            newPerson.setLocation(null);
        }
        worker.setName(person.getName());
        worker.setPerson(newPerson);
        worker.setSalary(salary);
        worker.setStartDate(startDate);
        if (person.getCoordinates() != null) {
            try {
                Coordinates coordinates = new Coordinates();
                coordinates.setX(Long.valueOf(person.getCoordinates().getX()));
                coordinates.setY(Float.valueOf(person.getCoordinates().getY()));
                worker.setCoordinates(coordinates);
            } catch (NumberFormatException e){
                errors.addError(422, "Coordinates x,y should be long and float");
                out.setStatus(422);
                try {
                    out.setMsg(mapper.writeValueAsString(errors));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
                return out;
            }
        } else {
            worker.setCoordinates(null);
        }
        worker.setStatus(Status.HIRED.toString());
        System.out.println(worker);
        try {
            HttpResponse<String> jsonPatchResponse = Unirest.post("https://127.0.0.1:8443/worker-0.0.1/api/workers/")
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(worker))
                    .asString();
            if (jsonPatchResponse.getStatus() == 200) {
                Worker outWorker = mapper.readValue(jsonPatchResponse.getBody(), Worker.class);
                out.setStatus(200);
                out.setMsg(mapper.writeValueAsString(outWorker));
                return out;
            } else if (jsonPatchResponse.getStatus() == 404){
                errors.addError(503, "Callable service is down");
                out.setStatus(503);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            } else {
                String msg = jsonPatchResponse.getBody();
                out.setStatus(jsonPatchResponse.getStatus());
                out.setMsg(msg);
                return out;
            }
        } catch (Exception e){
            errors.addError(503, "Callable service is down");
            try {
                out.setStatus(503);
                out.setMsg(mapper.writeValueAsString(errors));
                return out;
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
