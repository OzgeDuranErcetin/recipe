package com.abnamro.recipe.api.handler;

import com.abnamro.recipe.api.response.ErrorResponse;
import com.abnamro.recipe.constant.ErrorCode;
import com.abnamro.recipe.exception.InvalidDishTypeException;
import com.abnamro.recipe.exception.RecipeDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.abnamro.recipe.TestConstant.RECIPE_ID;
import static com.abnamro.recipe.constant.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeExceptionHandlerTest {

    @InjectMocks
    RecipeExceptionHandler recipeExceptionHandler;

    @Test
    void whenRecipeNotFoundException_thenReturnsErrorResponse() {
        ResponseEntity<ErrorResponse> response =
                recipeExceptionHandler.handleRecipeDoesNotExistException(
                        new RecipeDoesNotExistException(RECIPE_NOT_FOUND, RECIPE_ID));

        ErrorResponse errorResponse = response.getBody();

        assertNotNull(errorResponse);
        assertEquals(errorResponse.getCode(), RECIPE_NOT_FOUND.name());
        assertEquals(errorResponse.getMessage(), ErrorCode.RECIPE_NOT_FOUND.getMessage());
        assertNotNull(errorResponse.getDetails());
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void whenConstraintViolationException_thenReturnsErrorResponse() {
        Object obj = new Object() {};

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);

        ResponseEntity<ErrorResponse> response =
                recipeExceptionHandler.handleConstraintViolationException(
                        new ConstraintViolationException(violations));

        ErrorResponse errorResponse = response.getBody();

        assertNotNull(errorResponse);
        assertEquals(errorResponse.getCode(), ErrorCode.CONSTRAINT_VALIDATION.name());
        assertEquals(errorResponse.getMessage(), ErrorCode.CONSTRAINT_VALIDATION.getMessage());
        assertNotNull(errorResponse.getDetails());
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void whenInvalidDishTypeException_thenReturnsErrorResponse() {
        ResponseEntity<ErrorResponse> response =
                recipeExceptionHandler.handleInvalidDishTypeException(
                        new InvalidDishTypeException(DISH_TYPE_VALIDATION, "Invalid Dish Type"));

        ErrorResponse errorResponse = response.getBody();

        assertNotNull(errorResponse);
        assertEquals(errorResponse.getCode(), DISH_TYPE_VALIDATION.name());
        assertEquals(errorResponse.getMessage(), ErrorCode.DISH_TYPE_VALIDATION.getMessage());
        assertNotNull(errorResponse.getDetails());
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void whenMethodArgumentNotValidException_thenReturnsErrorResponse() {
        MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValidException.getMessage()).thenReturn("Method Argument Not Valid");

        ResponseEntity<ErrorResponse> response =
                recipeExceptionHandler.handleMethodArgumentNotValidException(methodArgumentNotValidException);

        ErrorResponse errorResponse = response.getBody();

        assertNotNull(errorResponse);
        assertEquals(errorResponse.getCode(), REQUEST_ARGUMENT_VALIDATION.name());
        assertEquals(errorResponse.getMessage(), ErrorCode.REQUEST_ARGUMENT_VALIDATION.getMessage());
        assertNotNull(errorResponse.getDetails());
        assertNotNull(errorResponse.getTimestamp());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}