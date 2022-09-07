package com.abnamro.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSearchDto {

    private String dishType;
    private String dishTypeNot;
    private String ingredientInclude;
    private String ingredientNotInclude;
    private Integer maxPortionSize;
    private Integer minPortionSize;
    private Integer portionSize;
    private String cookingInstructionsLike;
    private String cookingInstructionsNotLike;
}
