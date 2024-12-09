package com.example.springboot;

import com.example.springboot.POJO.HireWorkerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/hr")
public class HrController {

	private final HrService hrService;

	@Autowired
	public HrController(HrService hrService){
		this.hrService = hrService;
	}

	@PatchMapping("/fire/{worker-id}")
	public ResponseEntity<?> fireWorker(@PathVariable("worker-id") String workerID){
        Map.Entry<String, Integer> res = null;
        System.out.println(workerID);
        try {
            res = hrService.fireWorker(workerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(res.getValue()).body(res.getKey());
    }

    @PostMapping("/hire/{salary}/{start-date}")
    public ResponseEntity<?> hireWorker(@PathVariable("salary") String salary, @PathVariable("start-date") String startDate, @RequestBody HireWorkerDTO person) throws UnirestException, JsonProcessingException {
        var res = hrService.hireWorker(salary, startDate, person);
        return ResponseEntity.status(res.getValue()).body(res.getKey());
    }

}
