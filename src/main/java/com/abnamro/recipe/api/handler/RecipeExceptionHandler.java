package com.abnamro.recipe.api.handler;

import com.abnamro.recipe.api.response.ErrorResponse;
import com.abnamro.recipe.constant.ErrorCode;
import com.abnamro.recipe.exception.InvalidDishTypeException;
import com.abnamro.recipe.exception.RecipeDoesNotExistException;
import com.abnamro.recipe.exception.RecipeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import java.time.LocalDateTime;

import static com.abnamro.recipe.constant.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RecipeExceptionHandler {

    @ExceptionHandler(RecipeDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleRecipeDoesNotExistException(RecipeDoesNotExistException ex) {
        return ResponseEntity.status(NOT_FOUND).body(mapToErrorResponse(ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(mapToErrorResponse(CONSTRAINT_VALIDATION, new String[]{ex.getMessage()}));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(mapToErrorResponse(REQUEST_ARGUMENT_VALIDATION, new String[]{ex.getMessage()}));
    }

    @ExceptionHandler(InvalidDishTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDishTypeException(InvalidDishTypeException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(mapToErrorResponse(ex));
    }

    private ErrorResponse mapToErrorResponse(RecipeException ex) {
        return mapToErrorResponse(ex.getErrorCode(), ex.getDetails(), ex.getTimestamp());
    }

    private ErrorResponse mapToErrorResponse(ErrorCode code, Object details) {
        return mapToErrorResponse(code, details, LocalDateTime.now());
    }

    private ErrorResponse mapToErrorResponse(ErrorCode code, Object details, LocalDateTime timestamp) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .code(code.name())
                .message(code.getMessage())
                .details(details)
                .build();
    }
}
