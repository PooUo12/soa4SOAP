package com.example.springboot;

import com.example.springboot.POJO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.annotation.PostConstruct;
import jakarta.jws.WebService;
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
    public String fireWorker(String Id){

        Map.Entry<String, Integer> error = null;
        int workerId;

        try{
            workerId = Integer.parseInt(Id);
        } catch (NumberFormatException e){
            return "422: Id should be integer";
        }
//        https://127.0.0.1:8443/worker-0.0.1/api/workers/
        try {
            HttpResponse<String> jsonResponse = Unirest.get("https://127.0.0.1:8443/worker-0.0.1/api/workers/" + workerId)
                    .header("Content-Type", "application/json")
                    .asString();
            String responseBody = jsonResponse.getBody();
            if (jsonResponse.getStatus() == 404){
                return "503: " + "Callable service is down";
            }
            if (jsonResponse.getStatus() != 200) {
                return "422: Worker does not exist";
            }
            Worker worker = mapper.readValue(responseBody, Worker.class);
            if (worker.getStatus().equalsIgnoreCase(Status.FIRED.toString())) {
                return "422: Worker is already fired";
            }
            Worker out = new Worker();
            out.setName(worker.getName());
            out.setSalary(worker.getSalary());
            out.setCreationDate(worker.getCreationDate());
            out.setStartDate(worker.getStartDate());
            out.setEndDate(worker.getEndDate());
            out.setStatus(Status.FIRED.toString());
            HttpResponse<String> jsonPatchResponse = Unirest.patch("https://127.0.0.1:8443/worker-0.0.1/api/workers/" + workerId)
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(out))
                    .asString();
            if (jsonPatchResponse.getStatus() == 200) {
                return "200: Worker was fired";
            } else {
                String msg = jsonPatchResponse.getBody();
                return jsonPatchResponse.getStatus() + ": " +msg.substring(47, msg.length()- 5);
            }
        } catch (Exception e){
            return "503: " + "Callable service is down";
        }
    }

    @Transactional
    public String hireWorker(String salaryStr, String startDate, HireWorkerDTO person){

        int salary;

        try{
            salary = Integer.parseInt(salaryStr);
        } catch (NumberFormatException e){
            return "422: Salary should be integer";
        }


        Worker worker = new Worker();
        Person newPerson = new Person();
        newPerson.setBirthday(person.getBirthday());
        newPerson.setNationality(person.getNationality());
        newPerson.setEyeColor(person.getEyeColor());
        newPerson.setHairColor(person.getHairColor());
        if (person.getLocation() != null) {
            Location location = new Location();
            location.setName(person.getLocation().getName());
            location.setX(Double.valueOf(person.getLocation().getX()));
            location.setY(Double.valueOf(person.getLocation().getY()));
            location.setZ(Float.valueOf(person.getLocation().getZ()));
            newPerson.setLocation(location);
        } else {
            newPerson.setLocation(null);
        }
        worker.setName(person.getName());
        worker.setPerson(newPerson);
        worker.setSalary(salary);
        worker.setStartDate(startDate);
        if (person.getCoordinates() != null) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(Long.valueOf(person.getCoordinates().getX()));
            coordinates.setY(Float.valueOf(person.getCoordinates().getY()));
            worker.setCoordinates(coordinates);
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
                System.out.println(jsonPatchResponse.getBody());
                Worker outWorker = mapper.readValue(jsonPatchResponse.getBody(), Worker.class);
                return "200: " + outWorker.toString();
            } else if (jsonPatchResponse.getStatus() == 404){
                return "503: " + "Callable service is down";
            } else {
                String msg = jsonPatchResponse.getBody();
                return jsonPatchResponse.getStatus() + ": " +msg.substring(47, msg.length()- 5);
            }
        } catch (Exception e){
            return "503: " + "Callable service is down";
        }
    }

}
