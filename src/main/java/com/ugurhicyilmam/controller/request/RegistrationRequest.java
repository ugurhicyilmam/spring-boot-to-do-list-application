package com.ugurhicyilmam.controller.request;

import com.ugurhicyilmam.validation.UsernameNotTaken;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    @Size(min = 5, max = 60)
    @UsernameNotTaken
    private String username;
    @Size(min = 6, max = 60)
    private String password;
    @NotNull(message = "Password confirmation must match password")
    private String passwordConfirmation;

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
        checkPasswordConfirmation();
    }

    public void setPassword(String password) {
        this.password = password;
        checkPasswordConfirmation();
    }

    private void checkPasswordConfirmation() {
        if (this.password == null || this.passwordConfirmation == null)
            return;

        if (!this.password.equals(passwordConfirmation))
            this.passwordConfirmation = null;
    }
}
