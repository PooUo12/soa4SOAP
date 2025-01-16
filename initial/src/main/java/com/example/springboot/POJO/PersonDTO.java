package com.example.springboot.POJO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
public class PersonDTO implements Serializable {
    String birthday;
    String eyeColor;
    String hairColor;
    String nationality;
    LocationDTO location;
}
