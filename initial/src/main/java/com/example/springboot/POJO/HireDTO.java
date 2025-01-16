package com.example.springboot.POJO;

import lombok.Data;

@Data
public class HireDTO {

    private String salary;

    private String startDate;

    private String birthday;

    private String eyeColor;

    private String hairColor;

    private String nationality;

    private Location location;

    private Coordinates coordinates;

    private String name;
}
