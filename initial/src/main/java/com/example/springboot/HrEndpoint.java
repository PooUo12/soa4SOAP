//package com.example.springboot;
//
//import com.example.springboot.POJO.HireDTO;
//import com.example.springboot.POJO.HireWorkerDTO;
//import com.example.springboot.POJO.WorkerDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ws.server.endpoint.annotation.Endpoint;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
//
//
//@Endpoint
//public class HrEndpoint {
//    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";
//
//    private final HrServiceImpl hrServiceImpl;
//
//    @Autowired
//    public HrEndpoint(HrServiceImpl hrServiceImpl) {
//        this.hrServiceImpl = hrServiceImpl;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "fireRequest")
//    @ResponsePayload
//    public String fireWorker(@RequestPayload Integer workerId) {
//        String out;
//        try {
//            out = hrServiceImpl.fireWorker(String.valueOf(workerId)).toString();
//        } catch (Exception e){
//            out = "Error 500";
//        }
//        return out;
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "hireRequest")
//    @ResponsePayload
//    public WorkerDTO hireWorker(@RequestPayload HireDTO hireDTO) {
//        HireWorkerDTO hireWorkerDTO = new HireWorkerDTO();
//        hireWorkerDTO.setNationality(hireDTO.getNationality());
//        hireWorkerDTO.setName(hireDTO.getName());
//        hireWorkerDTO.setBirthday(hireDTO.getBirthday());
//        hireWorkerDTO.setLocation(hireDTO.getLocation());
//        hireWorkerDTO.setCoordinates(hireDTO.getCoordinates());
//        hireWorkerDTO.setEyeColor(hireDTO.getEyeColor());
//        hireWorkerDTO.setHairColor(hireDTO.getHairColor());
//        hireWorkerDTO.setNationality(hireDTO.getNationality());
//        try {
//            return (WorkerDTO) hrServiceImpl.hireWorker(hireDTO.getSalary(), hireDTO.getStartDate(), hireWorkerDTO);
//        } catch (Exception e){
//            return null;
//        }
//    }
//}