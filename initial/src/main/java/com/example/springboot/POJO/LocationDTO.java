package com.example.springboot.POJO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class LocationDTO implements Serializable {
    String name;
    String x;
    String y;
    String z;
}
