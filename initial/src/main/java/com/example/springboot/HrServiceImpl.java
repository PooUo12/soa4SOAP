package com.example.springboot;

import com.example.springboot.POJO.HireWorkerDTO;
import com.example.springboot.POJO.Person;
import com.example.springboot.POJO.Status;
import com.example.springboot.POJO.Worker;
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
            return "Wrong id";
//            return new AbstractMap.SimpleImmutableEntry<>("Id should be integer", 422);
        }
//        https://127.0.0.1:8443/worker-0.0.1/api/workers/
        try {
            HttpResponse<String> jsonResponse = Unirest.get("https://127.0.0.1:8443/worker-0.0.1/api/workers/" + workerId)
                    .header("Content-Type", "application/json")
                    .asString();
            String responseBody = jsonResponse.getBody();
            if (jsonResponse.getStatus() != 200) {
                return "Worker does not exist";
//                return new AbstractMap.SimpleImmutableEntry<>("Worker doesn't exist", 422);
            }
            Worker worker = mapper.readValue(responseBody, Worker.class);
            if (worker.getStatus().equalsIgnoreCase(Status.FIRED.toString())) {
                return "Worker is already fired";
//                return new AbstractMap.SimpleImmutableEntry<>("Worker is already fired", 422);
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
                return "Worker was fired";
//                return new AbstractMap.SimpleImmutableEntry<>("Worker was fired :(", 200);
            } else {
                return "500";
//                return new AbstractMap.SimpleImmutableEntry<>(jsonPatchResponse.getBody(), jsonPatchResponse.getStatus());
            }
        } catch (Exception e){
            return null;
        }
    }

    @Transactional
    public String hireWorker(String salaryStr, String startDate, HireWorkerDTO person){

        int salary;

        try{
            salary = Integer.parseInt(salaryStr);
        } catch (NumberFormatException e){
            return "Salary should be integer";
//            return new AbstractMap.SimpleImmutableEntry<>("Salary should be integer", 422);
        }


        Worker worker = new Worker();
        Person newPerson = new Person();
        newPerson.setBirthday(person.getBirthday());
        newPerson.setNationality(person.getNationality());
        newPerson.setEyeColor(person.getEyeColor());
        newPerson.setHairColor(person.getHairColor());
        newPerson.setLocation(person.getLocation());
        worker.setName(person.getName());
        worker.setPerson(newPerson);
        worker.setSalary(salary);
        worker.setStartDate(startDate);
        worker.setCoordinates(person.getCoordinates());
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
//                return new AbstractMap.SimpleImmutableEntry<>(outWorker, 200);
                return "Success";
            } else {
//                return new AbstractMap.SimpleImmutableEntry<>(jsonPatchResponse.getBody().toString(), jsonPatchResponse.getStatus());
                return "500";
            }
        } catch (Exception e){
            return "500";
        }
    }

}
