package com.example.qaguru_16_restassured.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUser {
    private String name;
    private String job;

}
