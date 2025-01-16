package com.example.springboot.POJO;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @XmlElement
    private int id;
    @XmlElement
    private Long x;
    @XmlElement
    private Float y;

    public Coordinates(Long x, Float y){
        this.x = x;
        this.y = y;
    }
}
