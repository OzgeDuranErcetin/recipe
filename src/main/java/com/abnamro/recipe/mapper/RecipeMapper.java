package com.abnamro.recipe.mapper;

import com.abnamro.recipe.config.MapperConfiguration;
import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.dto.IngredientDto;
import com.abnamro.recipe.dto.RecipeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        config = MapperConfiguration.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {

    RecipeDto recipeToRecipeDto(Recipe recipe);
    Recipe recipeDtoToRecipe(RecipeDto recipe);
    Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto);
}
