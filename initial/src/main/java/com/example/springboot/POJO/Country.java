package com.example.springboot.POJO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Country {

    @JsonProperty("france")
    FRANCE("france"),
    @JsonProperty("thailand")
    THAILAND("thailand"),
    @JsonProperty("south_korea")
    SOUTH_KOREA("south_korea"),
    @JsonProperty("north_korea")
    NORTH_KOREA("north_korea"),
    @JsonProperty("japan")
    JAPAN("japan");

    private final String title;

    Country(String title) {
        this.title = title;
    }
}
