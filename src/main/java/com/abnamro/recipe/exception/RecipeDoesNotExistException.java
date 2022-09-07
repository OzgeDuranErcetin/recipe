package com.abnamro.recipe.exception;

import com.abnamro.recipe.constant.ErrorCode;
import lombok.Getter;

@Getter
public class RecipeDoesNotExistException extends RecipeException{
    public RecipeDoesNotExistException(Object... details) {
        super(ErrorCode.RECIPE_NOT_FOUND, details);
    }
}
