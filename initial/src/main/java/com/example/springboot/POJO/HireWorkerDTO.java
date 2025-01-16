package com.example.springboot.POJO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HireWorkerDTO {
    private String birthday;

    private String eyeColor;

    private String hairColor;

    private String nationality;

    private Location location;

    private Coordinates coordinates;

    private String name;
}
