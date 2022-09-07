package com.abnamro.recipe.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientDto {
    private Long id;
    private String name;
}
