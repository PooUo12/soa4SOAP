package com.example.springboot.POJO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coordinates {

    private int id;

    private Long x;

    private Float y;

    public Coordinates(Long x, Float y){
        this.x = x;
        this.y = y;
    }
}
