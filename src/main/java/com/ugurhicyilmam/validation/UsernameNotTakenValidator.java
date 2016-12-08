package com.ugurhicyilmam.validation;

import com.ugurhicyilmam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameNotTakenValidator implements ConstraintValidator<UsernameNotTaken, String> {

   private final UserService userService;

   @Autowired
   public UsernameNotTakenValidator(UserService userService) {
      this.userService = userService;
   }

   public void initialize(UsernameNotTaken constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      return userService.findByUsername(obj) == null;
   }

}
