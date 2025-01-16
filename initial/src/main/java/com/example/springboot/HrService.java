package com.example.springboot;

import com.example.springboot.POJO.HireWorkerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Controller
@WebService(name="hrService")
public interface HrService {

    @WebMethod(action= "urn:FireWorker")
    public String fireWorker(@WebParam(name = "workerId", mode = WebParam.Mode.IN) String Id);

    @WebMethod(action= "urn:HireWorker")
    public String hireWorker(@WebParam(name = "Salary", mode = WebParam.Mode.IN) String salaryStr, @WebParam(name = "startDate", mode = WebParam.Mode.IN) String startDate, @WebParam(name = "person", mode = WebParam.Mode.IN) HireWorkerDTO person);
}
