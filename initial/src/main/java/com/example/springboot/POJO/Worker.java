package com.example.springboot.POJO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Data
public class Worker {
    private int id;
    private String name;
    private Coordinates coordinates;
    private String creationDate;
    private long salary;
    private String startDate;
    private String endDate;
    private String status;
    private Person person;

}
