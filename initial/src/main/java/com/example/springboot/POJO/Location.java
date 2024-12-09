package com.example.springboot.POJO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location {

    private int id;

    private Double x;

    private Double y;

    private Float z;

    private String name;

    public Location(Double x, Double y, Float z, String name){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
