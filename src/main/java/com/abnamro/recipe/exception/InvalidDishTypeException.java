package com.abnamro.recipe.exception;

import com.abnamro.recipe.constant.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidDishTypeException extends RecipeException{
    public InvalidDishTypeException(Object... details) {
        super(ErrorCode.DISH_TYPE_VALIDATION, details);
    }
}
