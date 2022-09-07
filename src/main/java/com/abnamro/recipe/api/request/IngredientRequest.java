package com.abnamro.recipe.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequest {

    @NotNull
    @Size(max = 30)
    private String name;
}
