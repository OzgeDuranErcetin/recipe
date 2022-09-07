package com.abnamro.recipe.api.mapper;

import com.abnamro.recipe.api.request.RecipeRequest;
import com.abnamro.recipe.api.response.RecipeResponse;
import com.abnamro.recipe.dto.RecipeDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.abnamro.recipe.RecipeFixture.defaultRecipeRequest;
import static com.abnamro.recipe.RecipeFixture.savedRecipeDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeContractMapperTest {

    @InjectMocks
    RecipeContractMapperImpl recipeContractMapper;

    @Test
    void whenCreateRecipeRequestToRecipeDtoMap_thenReturnRecipeDto() {
        RecipeRequest recipeRequest = defaultRecipeRequest();

        RecipeDto recipeDto = recipeContractMapper.recipeRequestToRecipeDto(recipeRequest);

        Assertions.assertNotNull(recipeDto);
        assertEquals(recipeRequest.getDishName(), recipeDto.getDishName());
        assertEquals(recipeRequest.getDishType(), recipeDto.getDishType());
        assertEquals(recipeRequest.getCookingInstructions(), recipeDto.getCookingInstructions());
        assertEquals(recipeRequest.getPortionSize(), recipeDto.getPortionSize());
        assertEquals(recipeRequest.getIngredients().size(), recipeDto.getIngredients().size());
    }

    @Test
    void whenCreateRecipeRequestNullToRecipeDtoMap_thenReturnNull() {
        assertNull(recipeContractMapper.recipeRequestToRecipeDto(null));
    }


    @Test
    void whenRecipeDtoToCreateRecipeResponseMap_thenReturnCreateRecipeResponse() {
        RecipeDto recipeDto = savedRecipeDto();

        RecipeResponse recipeResponse = recipeContractMapper
                .recipeDtoToRecipeResponse(recipeDto);

        Assertions.assertNotNull(recipeResponse);
        assertEquals(recipeDto.getId(), recipeResponse.getId());
        assertEquals(recipeDto.getDishName(), recipeResponse.getDishName());
        assertEquals(recipeDto.getDishType(), recipeResponse.getDishType());
        assertEquals(recipeDto.getCookingInstructions(), recipeResponse.getCookingInstructions());
        assertEquals(recipeDto.getPortionSize(), recipeResponse.getPortionSize());
        assertEquals(recipeDto.getCreateDate(), recipeResponse.getCreateDate());
        assertEquals(recipeDto.getIngredients().size(), recipeResponse.getIngredients().size());
    }

    @Test
    void whenRecipeDtoNullToCreateRecipeResponseMap_thenReturnNull() {
        assertNull(recipeContractMapper.recipeDtoToRecipeResponse(null));
    }
}