package com.abnamro.recipe.api.validator;

import com.abnamro.recipe.api.validator.annotation.DishType;
import com.abnamro.recipe.exception.InvalidDishTypeException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class DishTypeValidator implements ConstraintValidator<DishType,String> {
    @Override
    public void initialize(DishType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.nonNull(value)
                && Arrays.stream(com.abnamro.recipe.constant.DishType.values()).anyMatch(x->x.toString()
                .equalsIgnoreCase(value))){
            return true;
        }
        throw new InvalidDishTypeException(value);
    }
}
