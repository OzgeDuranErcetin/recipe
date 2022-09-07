package com.abnamro.recipe.service;

import com.abnamro.recipe.dto.RecipeDto;
import com.abnamro.recipe.dto.RecipeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    RecipeDto createRecipe(RecipeDto recipeDto);

    Page<RecipeDto> getRecipes(RecipeSearchDto recipeSearchDto, Pageable pageable);

    void deleteRecipe(Long recipeId);

    void updateRecipe(Long id, RecipeDto updatedRecipeDto);
}
