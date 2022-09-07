package com.abnamro.recipe.api.mapper;

import com.abnamro.recipe.api.request.RecipeRequest;
import com.abnamro.recipe.api.response.RecipeResponse;
import com.abnamro.recipe.config.MapperConfiguration;
import com.abnamro.recipe.dto.RecipeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        config = MapperConfiguration.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeContractMapper {

    RecipeDto recipeRequestToRecipeDto(RecipeRequest recipeRequest);
    RecipeResponse recipeDtoToRecipeResponse(RecipeDto recipe);
}
