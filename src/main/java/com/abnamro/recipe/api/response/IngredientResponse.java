package com.abnamro.recipe.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IngredientResponse {

    private Long id;
    private String name;
}
