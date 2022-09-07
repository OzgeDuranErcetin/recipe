package com.abnamro.recipe.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class RecipeDto {

    private Long id;
    private String dishName;
    private String dishType;
    private int portionSize;
    private String cookingInstructions;
    private Date createDate;
    private Date updateDate;
    private List<IngredientDto> ingredients;
}
