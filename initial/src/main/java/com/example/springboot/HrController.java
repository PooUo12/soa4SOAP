//package com.example.springboot;
//
//import com.example.springboot.POJO.HireWorkerDTO;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//
//import java.util.Map;
//
//@Controller
//public class HrController {
//
//	private final HrServiceImpl hrServiceImpl;
//
//	@Autowired
//	public HrController(HrServiceImpl hrServiceImpl){
//		this.hrServiceImpl = hrServiceImpl;
//	}
//
//	public Object fireWorker(String workerID){
//        Map.Entry<String, Integer> res = null;
//        System.out.println(workerID);
//        try {
//            res = hrServiceImpl.fireWorker(workerID);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(res.getValue()).body(res.getKey());
//    }
//
//    public Object hireWorker(String salary, String startDate, HireWorkerDTO person) throws UnirestException, JsonProcessingException {
//        var res = hrServiceImpl.hireWorker(salary, startDate, person);
//        return ResponseEntity.status(res.getValue()).body(res.getKey());
//    }
//
//}
