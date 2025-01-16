package com.example.springboot.POJO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Worker {
    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private Coordinates coordinates;
    @XmlElement
    private String creationDate;
    @XmlElement
    private long salary;
    @XmlElement
    private String startDate;
    @XmlElement
    private String endDate;
    @XmlElement
    private String status;
    @XmlElement
    private Person person;

}
