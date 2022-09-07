package com.abnamro.recipe;

import com.abnamro.recipe.api.request.RecipeRequest;
import com.abnamro.recipe.api.response.RecipeResponse;
import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.dto.RecipeDto;
import com.abnamro.recipe.dto.RecipeSearchDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.abnamro.recipe.IngredientFixture.*;
import static com.abnamro.recipe.TestConstant.*;

public class RecipeFixture {

    public static Recipe defaultRecipe() {
        return Recipe.builder().dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE).portionSize(RECIPE_PORTION_SIZE)
                .cookingInstructions(RECIPE_COOKING_INSTRUCTOR).createDate(RECIPE_CREATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, defaultIngredient()))).build();
    }

    public static Recipe savedRecipe() {
        return Recipe.builder().id(RECIPE_ID).dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE).portionSize(RECIPE_PORTION_SIZE)
                .cookingInstructions(RECIPE_COOKING_INSTRUCTOR).createDate(RECIPE_CREATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, savedIngredient()))).build();
    }

    public static RecipeDto defaultRecipeDto() {
        return RecipeDto.builder().dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE).portionSize(RECIPE_PORTION_SIZE)
                .cookingInstructions(RECIPE_COOKING_INSTRUCTOR).createDate(RECIPE_CREATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, defaultIngredientDto()))).build();
    }

    public static RecipeDto savedRecipeDto() {
        return RecipeDto.builder().id(RECIPE_ID).dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE).portionSize(RECIPE_PORTION_SIZE)
                .cookingInstructions(RECIPE_COOKING_INSTRUCTOR).createDate(RECIPE_CREATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, savedIngredientDto()))).build();
    }

    public static Recipe updatedRecipe() {
        return Recipe.builder().id(RECIPE_ID).dishName("NEW" + DISH_NAME).dishType(VEGETARIAN_DISH_TYPE)
                .portionSize(RECIPE_PORTION_SIZE).cookingInstructions(RECIPE_COOKING_INSTRUCTOR)
                .createDate(RECIPE_CREATE_DATE).updateDate(RECIPE_UPDATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(5, savedIngredient()))).build();
    }

    public static RecipeDto updatedRecipeDto() {
        return RecipeDto.builder().id(RECIPE_ID).dishName("NEW" + DISH_NAME).dishType(VEGETARIAN_DISH_TYPE)
                .portionSize(RECIPE_PORTION_SIZE).cookingInstructions(RECIPE_COOKING_INSTRUCTOR)
                .createDate(RECIPE_CREATE_DATE).updateDate(RECIPE_UPDATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, savedIngredientDto()))).build();
    }

    public static RecipeRequest defaultRecipeRequest() {
        return RecipeRequest.builder().dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE)
                .portionSize(RECIPE_PORTION_SIZE).cookingInstructions(RECIPE_COOKING_INSTRUCTOR)
                .ingredients(new ArrayList<>(Collections.nCopies(4, defaultIngredientRequest()))).build();
    }

    public static RecipeResponse defaultRecipeResponse() {
        return RecipeResponse.builder().id(RECIPE_ID).dishName(DISH_NAME).dishType(VEGETARIAN_DISH_TYPE).portionSize(RECIPE_PORTION_SIZE)
                .cookingInstructions(RECIPE_COOKING_INSTRUCTOR).createDate(RECIPE_CREATE_DATE)
                .ingredients(new ArrayList<>(Collections.nCopies(4, defaultIngredientResponse()))).build();
    }

    public static RecipeSearchDto defaultRecipeSearchDto() {
        return RecipeSearchDto.builder().dishType(VEGETARIAN_DISH_TYPE).minPortionSize(4).cookingInstructionsLike(RECIPE_COOKING_INSTRUCTOR).build();
    }

    public static Recipe vegetarianRecipe() {

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(buildIngredient("Cream"));
        ingredientList.add(buildIngredient("Flour"));
        ingredientList.add(buildIngredient("Water"));
        ingredientList.add(buildIngredient("Parmesan"));
        ingredientList.add(buildIngredient("Milk"));
        ingredientList.add(buildIngredient("Salt"));
        return Recipe.builder().dishName("Pasta").dishType(VEGETARIAN_DISH_TYPE).portionSize(4)
                .cookingInstructions("Make pasta with water, flour and milk. Prepare a sauce with cream and milk. Mix pasta and sauce in pot")
                .ingredients(ingredientList).build();
    }

    public static Recipe veganRecipe() {

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(buildIngredient("Tomato"));
        ingredientList.add(buildIngredient("Lettuce"));
        ingredientList.add(buildIngredient("Lemon"));
        ingredientList.add(buildIngredient("Olive oil"));
        return Recipe.builder().dishName("Salad").dishType(VEGAN_DISH_TYPE).portionSize(1)
                .cookingInstructions("Crop vegetables, add olive oil, mix them")
                .ingredients(ingredientList).build();
    }

    public static Recipe meatRecipe() {

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(buildIngredient("Tomato"));
        ingredientList.add(buildIngredient("Beef"));
        ingredientList.add(buildIngredient("Parmesan"));
        ingredientList.add(buildIngredient("Salt"));
        ingredientList.add(buildIngredient("Pepper"));
        return Recipe.builder().dishName("Lasagna").dishType(MEAT_DISH_TYPE).portionSize(6)
                .cookingInstructions("Make a sauce with tomato adn beef. Cooked with oven.")
                .ingredients(ingredientList).build();
    }
}
