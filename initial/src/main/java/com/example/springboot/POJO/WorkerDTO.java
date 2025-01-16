package com.example.springboot.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Data
@XmlRootElement
public class WorkerDTO implements Serializable {
    private int id;
    private String name;
    private CoordinatesDTO coordinates;
    private String salary;
    private String startDate;
    private String endDate;
    private String status;
    private PersonDTO person;
    private String creationDate;
}
