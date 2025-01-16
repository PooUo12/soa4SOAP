package com.example.springboot.POJO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    @XmlElement
    private int id;
    @XmlElement
    private String birthday;
    @XmlElement
    private String eyeColor;
    @XmlElement
    private String hairColor;
    @XmlElement
    private String nationality;
    @XmlElement
    private Location location;

}
