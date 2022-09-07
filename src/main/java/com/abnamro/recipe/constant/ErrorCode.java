package com.abnamro.recipe.constant;

public enum ErrorCode {
    RECIPE_NOT_FOUND("Recipe not found!"),
    CONSTRAINT_VALIDATION("Request is not valid!"),
    REQUEST_ARGUMENT_VALIDATION("Request is not valid!"),
    DISH_TYPE_VALIDATION("Dish Type is not valid!");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
