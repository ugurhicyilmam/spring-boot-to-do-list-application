package com.ugurhicyilmam.controller.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class TodoCreationRequest {
    @Size(min = 1, max = 255)
    private String todo;
}
