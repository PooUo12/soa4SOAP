package com.example.springboot.POJO;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)

public class Location {
    @XmlElement
    private Double x;
    @XmlElement
    private Double y;
    @XmlElement
    private Float z;
    @XmlElement
    private String name;

}
