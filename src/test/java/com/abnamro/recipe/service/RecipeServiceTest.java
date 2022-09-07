package com.abnamro.recipe.service;

import com.abnamro.recipe.constant.ErrorCode;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.dto.RecipeDto;
import com.abnamro.recipe.exception.RecipeDoesNotExistException;
import com.abnamro.recipe.mapper.RecipeMapper;
import com.abnamro.recipe.repository.RecipeRepository;
import com.abnamro.recipe.dto.RecipeSearchDto;
import com.abnamro.recipe.search.RecipeSpecificationGenerator;
import com.abnamro.recipe.search.RecipeSpecificationGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static com.abnamro.recipe.IngredientFixture.updatedIngredient;
import static com.abnamro.recipe.RecipeFixture.*;
import static com.abnamro.recipe.TestConstant.RECIPE_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @InjectMocks
    private RecipeServiceImpl recipeService;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private RecipeSpecificationGenerator recipeSpecificationGenerator;

    @Test
    void whenRecipeCreate_thenReturnCreatedRecipe(){
        Recipe recipe = defaultRecipe();
        RecipeDto recipeDto = defaultRecipeDto();
        Recipe savedRecipe = savedRecipe();
        RecipeDto savedRecipeDto = savedRecipeDto();

        when(recipeMapper.recipeDtoToRecipe(recipeDto)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(savedRecipe);
        when(recipeMapper.recipeToRecipeDto(savedRecipe)).thenReturn(savedRecipeDto);

        RecipeDto response = recipeService.createRecipe(recipeDto);

        assertEquals(savedRecipeDto.getId(), response.getId());
        assertEquals(savedRecipeDto.getDishName(), response.getDishName());
        assertEquals(savedRecipeDto.getPortionSize(), response.getPortionSize());
        assertEquals(savedRecipeDto.getDishType(), response.getDishType());
        assertEquals(savedRecipeDto.getCookingInstructions(), response.getCookingInstructions());
        assertEquals(savedRecipeDto.getIngredients().size(), response.getIngredients().size());
        assertEquals(savedRecipeDto.getCreateDate(), response.getCreateDate());
        assertNull(response.getUpdateDate());

        verify(recipeMapper).recipeDtoToRecipe(recipeDto);
        verify(recipeRepository).save(recipe);
        verify(recipeMapper).recipeToRecipeDto(savedRecipe);
    }

    @Test
    void whenRecipeListRetrieve_thenReturnFilteredRecipes(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> pageRecipe = new PageImpl<>(Collections.nCopies(4, savedRecipe()));
        RecipeSearchDto recipeSearchDto = defaultRecipeSearchDto();
        Specification<Recipe> recipeSpecification = new RecipeSpecificationGeneratorImpl().generate(recipeSearchDto);

        when(recipeSpecificationGenerator.generate(recipeSearchDto)).thenReturn(recipeSpecification);
        when(recipeRepository.findAll(recipeSpecification, pageable)).thenReturn(pageRecipe);
        when(recipeMapper.recipeToRecipeDto(any())).thenReturn(savedRecipeDto());

        Page<RecipeDto> response = recipeService.getRecipes(recipeSearchDto, pageable);

        assertEquals(pageRecipe.getSize(), response.getSize());
        assertEquals(pageRecipe.getContent().get(0).getId(), response.getContent().get(0).getId());
        assertEquals(pageRecipe.getContent().get(0).getDishName(), response.getContent().get(0).getDishName());
        assertEquals(pageRecipe.getContent().get(0).getPortionSize(), response.getContent().get(0).getPortionSize());
        assertEquals(pageRecipe.getContent().get(0).getDishType(), response.getContent().get(0).getDishType());
        assertEquals(pageRecipe.getContent().get(0).getCookingInstructions(), response.getContent().get(0).getCookingInstructions());
        assertEquals(pageRecipe.getContent().get(0).getIngredients().size(), response.getContent().get(0).getIngredients().size());
        assertEquals(pageRecipe.getContent().get(0).getCreateDate(), response.getContent().get(0).getCreateDate());
        assertEquals(pageRecipe.getContent().get(0).getUpdateDate(), response.getContent().get(0).getUpdateDate());

        verify(recipeMapper, times(pageRecipe.getSize())).recipeToRecipeDto(any());
        verify(recipeRepository).findAll(recipeSpecification, pageable);
        verify(recipeSpecificationGenerator).generate(recipeSearchDto);
    }

    @Test
    void whenExistingRecipeUpdate_thenReturnRecipe(){
        Recipe savedRecipe = savedRecipe();
        Recipe updatedRecipe = updatedRecipe();
        RecipeDto updatedRecipeDto = updatedRecipeDto();

        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(savedRecipe));
        when(recipeRepository.save(any())).thenReturn(updatedRecipe);
        when(recipeMapper.ingredientDtoToIngredient(any())).thenReturn(updatedIngredient());

        recipeService.updateRecipe(RECIPE_ID, updatedRecipeDto);

        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository).save(any());
        verify(recipeMapper, times(updatedRecipeDto.getIngredients().size())).ingredientDtoToIngredient(any());
    }

    @Test
    void whenNonExistingRecipeUpdate_thenThrowsRecipeDoesNotExistException(){
        Long nonExistingRecipeId = 5L;
        RecipeDto updatedRecipeDto = updatedRecipeDto();

        when(recipeRepository.findById(nonExistingRecipeId)).thenReturn(Optional.empty());

        RecipeDoesNotExistException exception = assertThrows(
                RecipeDoesNotExistException.class,
                () -> recipeService.updateRecipe(nonExistingRecipeId, updatedRecipeDto)
        );

        assertEquals(ErrorCode.RECIPE_NOT_FOUND.getMessage(), exception.getErrorCode().getMessage());

        verify(recipeRepository).findById(nonExistingRecipeId);
        verify(recipeRepository, never()).save(any());
        verify(recipeMapper, never()).ingredientDtoToIngredient(any());
    }

    @Test
    void whenExistingRecipeDelete_thenReturnVoid() {
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(savedRecipe()));

        recipeService.deleteRecipe(RECIPE_ID);

        verify(recipeRepository).deleteById(RECIPE_ID);
    }

    @Test
    void whenNonExistingRecipeDelete_thenThrowsRecipeDoesNotExistException() {
        Long nonExistingRecipeId = 5L;

        when(recipeRepository.findById(nonExistingRecipeId)).thenReturn(Optional.empty());

        RecipeDoesNotExistException exception = assertThrows(
                RecipeDoesNotExistException.class,
                () -> recipeService.deleteRecipe(nonExistingRecipeId)
        );

        assertEquals(ErrorCode.RECIPE_NOT_FOUND.getMessage(), exception.getErrorCode().getMessage());

        verify(recipeRepository).findById(nonExistingRecipeId);
        verify(recipeRepository, never()).deleteById(any());
    }
}
