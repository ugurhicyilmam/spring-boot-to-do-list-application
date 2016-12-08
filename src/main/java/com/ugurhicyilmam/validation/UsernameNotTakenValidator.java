package com.ugurhicyilmam.validation;

import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameNotTakenValidator implements ConstraintValidator<UsernameNotTaken, String> {

   @Autowired
   private UserService userService;

   public void initialize(UsernameNotTaken constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      return userService.findByUsername(obj) == null;
   }

}
