package com.example.springboot.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Status {
    @JsonProperty(value = "fired")
    FIRED("fired"),
    @JsonProperty(value = "hired")
    HIRED("hired"),
    @JsonProperty(value = "recommended_for_promotion")
    RECOMMENDED_FOR_PROMOTION("recommended_for_promotion"),
    @JsonProperty(value = "regular")
    REGULAR("regular"),
    @JsonProperty(value = "probation")
    PROBATION("probation");

    private final String title;

    Status(String title) {
        this.title = title;
    }
}
