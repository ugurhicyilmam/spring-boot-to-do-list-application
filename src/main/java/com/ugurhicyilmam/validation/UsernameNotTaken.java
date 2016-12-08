package com.ugurhicyilmam.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameNotTakenValidator.class)
public @interface UsernameNotTaken {

    String message() default "This username has already taken";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
