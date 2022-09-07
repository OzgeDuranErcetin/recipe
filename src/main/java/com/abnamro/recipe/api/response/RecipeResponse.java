package com.abnamro.recipe.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class RecipeResponse {

    private Long id;
    private String dishName;
    private String dishType;
    private int portionSize;
    private String cookingInstructions;
    private Date createDate;
    private List<IngredientResponse> ingredients;
}
