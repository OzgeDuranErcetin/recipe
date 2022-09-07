package com.abnamro.recipe.api.validator.annotation;

import com.abnamro.recipe.api.validator.DishTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {DishTypeValidator.class})
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DishType {

    Class<?>[] groups() default {};

    String message() default "Dish Type should be Vegetarian or Vegan or Meat ";

    Class<? extends Payload>[] payload() default {};
}