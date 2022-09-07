package com.abnamro.recipe.service;

import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.dto.IngredientDto;
import com.abnamro.recipe.dto.RecipeDto;
import com.abnamro.recipe.exception.RecipeDoesNotExistException;
import com.abnamro.recipe.mapper.RecipeMapper;
import com.abnamro.recipe.repository.RecipeRepository;
import com.abnamro.recipe.dto.RecipeSearchDto;
import com.abnamro.recipe.search.RecipeSpecificationGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeSpecificationGenerator recipeSpecificationGenerator;

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        return recipeMapper.recipeToRecipeDto(recipeRepository.save(recipe));
    }

    @Override
    public Page<RecipeDto> getRecipes(RecipeSearchDto recipeSearchDto, Pageable pageable) {
        return new PageImpl<>(recipeRepository.findAll(recipeSpecificationGenerator.generate(recipeSearchDto), pageable)
                .getContent().stream().map(recipeMapper::recipeToRecipeDto).toList());
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeDoesNotExistException(recipeId));

        recipeRepository.deleteById(recipe.getId());
    }

    @Override
    public void updateRecipe(Long id, RecipeDto updatedRecipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeDoesNotExistException(updatedRecipeDto.getId()));

        recipe.setDishName(updatedRecipeDto.getDishName());
        recipe.setCookingInstructions(updatedRecipeDto.getCookingInstructions());
        recipe.setPortionSize(updatedRecipeDto.getPortionSize());
        recipe.setDishType(updatedRecipeDto.getDishType());
        recipe.getIngredients().clear();
        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientDto ingredientDto : updatedRecipeDto.getIngredients()) {
            ingredients.add(recipeMapper.ingredientDtoToIngredient(ingredientDto));
        }
        recipe.getIngredients().addAll(ingredients);

        recipeRepository.save(recipe);
    }
}
