package com.ugurhicyilmam.controller.request;

import com.ugurhicyilmam.validation.UsernameNotTaken;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
public class RegistrationRequest {
    @Size(min = 5, max = 60)
    @UsernameNotTaken
    private String username;
    @Size(min = 6, max = 60)
    private String password;
    private String passwordConfirmation;

    @AssertTrue(message = "Password confirmation must match password")
    private boolean isPasswordConfirmation() {
        return Objects.equals(password, passwordConfirmation);
    }
}
