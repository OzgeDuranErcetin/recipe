package com.abnamro.recipe.exception;

import com.abnamro.recipe.constant.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecipeException extends RuntimeException{
    private final LocalDateTime timestamp;
    private final ErrorCode errorCode;
    private final transient Object[] details;
    public RecipeException(ErrorCode errorCode, Object... details) {
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.details = details;
    }
}
