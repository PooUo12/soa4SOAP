package com.example.springboot.POJO;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Errors implements Serializable {

    List<Error> errors = new ArrayList<>();

    public void addError(int status, String error){
        for (Error error1: errors){
            if (error1.error_code == status){
                error1.error_message.add(error);
                return;
            }
        }
        List<String> errs = new ArrayList<>();
        errs.add(error);
        errors.add(new Error(status, errs));
    }

}
