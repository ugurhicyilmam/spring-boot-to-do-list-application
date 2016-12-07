package com.ugurhicyilmam.controller.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String passwordConfirmation;
}
