package com.example.springboot.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum HairColor {
    @JsonProperty("green")
    GREEN("green"),
    @JsonProperty("black")
    BLACK("black"),
    @JsonProperty("blue")
    BLUE("blue");

    private final String title;

    HairColor(String title) {
        this.title = title;
    }
}
