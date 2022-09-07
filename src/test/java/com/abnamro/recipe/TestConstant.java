package com.abnamro.recipe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstant {

    public static final Long RECIPE_ID = 1L;
    public static final String DISH_NAME = "Tomato Soup";
    public static final String VEGETARIAN_DISH_TYPE = "Vegetarian";
    public static final String VEGAN_DISH_TYPE = "Vegan";
    public static final String MEAT_DISH_TYPE = "Meat";
    public static final int RECIPE_PORTION_SIZE = 4;
    public static final String RECIPE_COOKING_INSTRUCTOR = "pan";
    public static final Date RECIPE_CREATE_DATE = Calendar.getInstance().getTime();
    public static final Date RECIPE_UPDATE_DATE = Calendar.getInstance().getTime();
    public static final Long INGREDIENT_ID = 1L;
    public static final String INGREDIENT_NAME = "Tomato";
}
