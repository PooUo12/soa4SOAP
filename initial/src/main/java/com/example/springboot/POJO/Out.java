package com.example.springboot.POJO;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement
@Data
public class Out {
    private int status;
    private String msg;
}
