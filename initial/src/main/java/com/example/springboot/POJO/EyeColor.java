package com.example.springboot.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum EyeColor {
    @JsonProperty(value = "green")
    GREEN ("green"),
    @JsonProperty(value = "white")
    WHITE ("white"),
    @JsonProperty(value = "brown")
    BROWN ("brown");

    private final String title;

    EyeColor(String title) {
        this.title = title;
    }

}
