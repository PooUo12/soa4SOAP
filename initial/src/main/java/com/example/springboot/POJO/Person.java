package com.example.springboot.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {

    private int id;

    private String birthday;

    private String eyeColor;

    private String hairColor;

    private String nationality;

    private Location location;

}
