package com.abnamro.recipe.api.request;

import com.abnamro.recipe.api.validator.annotation.DishType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {

    @NotNull
    @Size(max = 100)
    private String dishName;
    @NotNull
    @DishType
    @Size(max = 30)
    private String dishType;
    @NotNull
    @Min(1)
    private int portionSize;
    private String cookingInstructions;
    private List<IngredientRequest> ingredients;
}
