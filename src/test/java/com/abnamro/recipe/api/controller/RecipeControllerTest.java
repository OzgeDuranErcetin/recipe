package com.abnamro.recipe.api.controller;

import com.abnamro.recipe.api.mapper.RecipeContractMapper;
import com.abnamro.recipe.api.request.RecipeRequest;
import com.abnamro.recipe.api.response.RecipeResponse;
import com.abnamro.recipe.dto.RecipeDto;
import com.abnamro.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static com.abnamro.recipe.RecipeFixture.*;
import static com.abnamro.recipe.TestConstant.RECIPE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;
    @Mock
    private RecipeService recipeService;
    @Mock
    RecipeContractMapper recipeContractMapper;

    @Test
    void whenRecipeCreate_thenReturnCreatedRecipe() {
        RecipeRequest recipeRequest = defaultRecipeRequest();
        RecipeDto recipeDto = defaultRecipeDto();
        RecipeDto createdRecipeDto = savedRecipeDto();
        RecipeResponse recipeResponse = defaultRecipeResponse();

        when(recipeContractMapper.recipeRequestToRecipeDto(recipeRequest)).thenReturn(recipeDto);
        when(recipeService.createRecipe(recipeDto)).thenReturn(createdRecipeDto);
        when(recipeContractMapper.recipeDtoToRecipeResponse(createdRecipeDto)).thenReturn(recipeResponse);

        RecipeResponse response = recipeController.createRecipe(recipeRequest);

        assertEquals(recipeResponse.getId(), response.getId());
        assertEquals(recipeResponse.getDishName(), response.getDishName());
        assertEquals(recipeResponse.getDishType(), response.getDishType());
        assertEquals(recipeResponse.getPortionSize(), response.getPortionSize());
        assertEquals(recipeResponse.getCookingInstructions(), response.getCookingInstructions());
        assertEquals(recipeResponse.getIngredients().size(), response.getIngredients().size());
        assertEquals(recipeResponse.getIngredients().get(0).getName(), response.getIngredients().get(0).getName());
        assertEquals(recipeResponse.getIngredients().get(0).getId(), response.getIngredients().get(0).getId());
        assertEquals(recipeResponse.getCreateDate(), response.getCreateDate());

        verify(recipeContractMapper).recipeRequestToRecipeDto(recipeRequest);
        verify(recipeContractMapper).recipeDtoToRecipeResponse(createdRecipeDto);
        verify(recipeService).createRecipe(recipeDto);
    }

    @Test
    void whenRecipeDelete_thenReturnVoid() {
        recipeController.deleteRecipe(RECIPE_ID);

        verify(recipeService).deleteRecipe(RECIPE_ID);
    }

    @Test
    void whenRecipeUpdate_thenReturnVoid() {
        RecipeRequest updateRecipeRequest = defaultRecipeRequest();
        RecipeDto recipeDto = defaultRecipeDto();

        when(recipeContractMapper.recipeRequestToRecipeDto(updateRecipeRequest)).thenReturn(recipeDto);

        recipeController.updateRecipe(RECIPE_ID, updateRecipeRequest);

        verify(recipeContractMapper).recipeRequestToRecipeDto(updateRecipeRequest);
        verify(recipeService).updateRecipe(RECIPE_ID, recipeDto);
    }

    @Test
    void whenRecipeListRetrieve_thenReturnFilteredRecipes(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipeDto> pageRecipeDto = new PageImpl<>(Collections.nCopies(4, savedRecipeDto()));

        when(recipeService.getRecipes(any(), eq(pageable))).thenReturn(pageRecipeDto);
        when(recipeContractMapper.recipeDtoToRecipeResponse(any())).thenReturn(defaultRecipeResponse());

        Page<RecipeResponse> response = recipeController.getRecipes(
                "TomatoSoup", null,null,null,
                null,null,null,null,null, pageable);

        assertEquals(pageRecipeDto.getSize(), response.getSize());
        assertEquals(pageRecipeDto.getContent().get(0).getId(), response.getContent().get(0).getId());
        assertEquals(pageRecipeDto.getContent().get(0).getDishName(), response.getContent().get(0).getDishName());
        assertEquals(pageRecipeDto.getContent().get(0).getPortionSize(), response.getContent().get(0).getPortionSize());
        assertEquals(pageRecipeDto.getContent().get(0).getDishType(), response.getContent().get(0).getDishType());
        assertEquals(pageRecipeDto.getContent().get(0).getCookingInstructions(), response.getContent().get(0).getCookingInstructions());
        assertEquals(pageRecipeDto.getContent().get(0).getIngredients().size(), response.getContent().get(0).getIngredients().size());

        verify(recipeContractMapper, times(4)).recipeDtoToRecipeResponse(any());
        verify(recipeService).getRecipes(any(), eq(pageable));
    }
}