package com.abnamro.recipe.api.validator;

import com.abnamro.recipe.constant.ErrorCode;
import com.abnamro.recipe.exception.InvalidDishTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static com.abnamro.recipe.TestConstant.VEGETARIAN_DISH_TYPE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishTypeValidatorTest {

    @InjectMocks
    DishTypeValidator dishTypeValidator;
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    void whenDishTypeValid_thenReturnTrue() {
        boolean response = dishTypeValidator.isValid(VEGETARIAN_DISH_TYPE, constraintValidatorContext);

        assertTrue(response);
    }

    @Test
    void whenDishTypeInvalidValid_thenThrowInvalidDishTypeException() {
        InvalidDishTypeException exception = assertThrows(
                InvalidDishTypeException.class,
                () -> dishTypeValidator.isValid("Invalid Dish Type", constraintValidatorContext)
        );

        assertEquals(ErrorCode.DISH_TYPE_VALIDATION.getMessage(), exception.getErrorCode().getMessage());
    }
}