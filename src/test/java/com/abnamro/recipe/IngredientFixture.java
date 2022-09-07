package com.abnamro.recipe;

import com.abnamro.recipe.api.request.IngredientRequest;
import com.abnamro.recipe.api.response.IngredientResponse;
import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.dto.IngredientDto;

import static com.abnamro.recipe.TestConstant.*;

public class IngredientFixture {

    public static Ingredient defaultIngredient(){
        return Ingredient.builder().name(INGREDIENT_NAME)
                .build();
    }

    public static IngredientDto defaultIngredientDto(){
        return IngredientDto.builder().name(INGREDIENT_NAME)
                .build();
    }

    public static Ingredient savedIngredient(){
        return Ingredient.builder().id(INGREDIENT_ID).name(INGREDIENT_NAME)
                .build();
    }

    public static IngredientDto savedIngredientDto(){
        return IngredientDto.builder().id(INGREDIENT_ID).name(INGREDIENT_NAME)
                .build();
    }

    public static Ingredient updatedIngredient(){
        return Ingredient.builder().id(INGREDIENT_ID).name(INGREDIENT_NAME)
                .build();
    }

    public static IngredientRequest defaultIngredientRequest(){
        return IngredientRequest.builder().name(INGREDIENT_NAME).build();
    }

    public static IngredientResponse defaultIngredientResponse(){
        return IngredientResponse.builder().id(INGREDIENT_ID).name(INGREDIENT_NAME)
                .build();
    }

    public static Ingredient buildIngredient(String name){
        return Ingredient.builder().name(name).build();
    }
}
