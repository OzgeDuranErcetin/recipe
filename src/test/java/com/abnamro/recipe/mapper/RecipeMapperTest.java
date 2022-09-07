package com.abnamro.recipe.mapper;

import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.dto.IngredientDto;
import com.abnamro.recipe.dto.RecipeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.abnamro.recipe.IngredientFixture.savedIngredient;
import static com.abnamro.recipe.IngredientFixture.savedIngredientDto;
import static com.abnamro.recipe.RecipeFixture.savedRecipe;
import static com.abnamro.recipe.RecipeFixture.savedRecipeDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeMapperTest {

    @InjectMocks
    RecipeMapperImpl recipeMapper;

    @Test
    void whenRecipeToRecipeDtoMap_thenReturnRecipeDto() {
        Recipe recipe = savedRecipe();

        RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipe);

        Assertions.assertNotNull(recipeDto);
        assertEquals(recipe.getId(), recipeDto.getId());
        assertEquals(recipe.getDishName(), recipeDto.getDishName());
        assertEquals(recipe.getDishType(), recipeDto.getDishType());
        assertEquals(recipe.getCookingInstructions(), recipeDto.getCookingInstructions());
        assertEquals(recipe.getPortionSize(), recipeDto.getPortionSize());
        assertEquals(recipe.getCreateDate(), recipeDto.getCreateDate());
        assertEquals(recipe.getIngredients().size(), recipeDto.getIngredients().size());
    }

    @Test
    void whenRecipeNullForRecipeDtoMap_thenReturnNull() {
        assertNull(recipeMapper.recipeToRecipeDto(null));
    }

    @Test
    void whenRecipeDtoToRecipeMap_thenReturnRecipe() {
        RecipeDto recipeDto = savedRecipeDto();

        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);

        Assertions.assertNotNull(recipe);
        assertEquals(recipeDto.getId(), recipe.getId());
        assertEquals(recipeDto.getDishName(),recipe.getDishName());
        assertEquals(recipeDto.getDishType(), recipe.getDishType());
        assertEquals(recipeDto.getCookingInstructions(), recipe.getCookingInstructions());
        assertEquals(recipeDto.getPortionSize(), recipe.getPortionSize());
        assertEquals(recipeDto.getCreateDate(), recipe.getCreateDate());
        assertEquals(recipeDto.getIngredients().size(), recipe.getIngredients().size());
    }

    @Test
    void whenRecipeDtoNullForRecipeMap_thenReturnNull() {
        assertNull(recipeMapper.recipeDtoToRecipe(null));
    }

    @Test
    void whenIngredientToIngredientDtoMap_thenReturnIngredientDto(){
        Ingredient ingredient = savedIngredient();

        IngredientDto ingredientDto = recipeMapper.ingredientToIngredientDto(ingredient);

        assertEquals(ingredient.getId(), ingredientDto.getId());
        assertEquals(ingredient.getName(), ingredientDto.getName());
    }

    @Test
    void whenIngredientNullForIngredientDtoMap_thenReturnNull() {
        assertNull(recipeMapper.ingredientToIngredientDto(null));
    }

    @Test
    void whenIngredientDtoToIngredientMap_thenReturnIngredient(){
        IngredientDto ingredientDto = savedIngredientDto();

        Ingredient ingredient = recipeMapper.ingredientDtoToIngredient(ingredientDto);

        assertEquals(ingredientDto.getId(), ingredient.getId());
        assertEquals(ingredientDto.getName(), ingredient.getName());
    }

    @Test
    void whenIngredientDtoNullForIngredientMap_thenReturnNull() {
        assertNull(recipeMapper.ingredientDtoToIngredient(null));
    }
}