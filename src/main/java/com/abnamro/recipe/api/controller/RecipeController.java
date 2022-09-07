package com.abnamro.recipe.api.controller;

import com.abnamro.recipe.api.mapper.RecipeContractMapper;
import com.abnamro.recipe.api.request.RecipeRequest;
import com.abnamro.recipe.api.response.RecipeResponse;
import com.abnamro.recipe.dto.RecipeSearchDto;
import com.abnamro.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeContractMapper recipeContractMapper;

    @PostMapping
    @Operation(summary = "Create Recipe",
            description = "This endpoint creates recipe and returns created recipe",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Create recipe request accepted, returns created recipe"),
                    @ApiResponse(responseCode = "500",
                            description = "Create recipe service encountered an unexpected internal error"),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid request")
            })
    @ResponseStatus(code = HttpStatus.CREATED)
    public RecipeResponse createRecipe(@NotNull @Valid @RequestBody RecipeRequest recipeRequest) {
        return recipeContractMapper.recipeDtoToRecipeResponse(
                recipeService.createRecipe(
                        recipeContractMapper.recipeRequestToRecipeDto(recipeRequest)));
    }

    @PutMapping("{id}")
    @Operation(summary = "Update Recipe",
            description = "This endpoint updates recipe",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Update recipe request accepted and recipe updated"),
                    @ApiResponse(responseCode = "500",
                            description = "Update recipe service encountered an unexpected internal error"),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid request"),
                    @ApiResponse(responseCode = "404",
                            description = "Update recipe request accepted, but there is no recipe for requested id")
            })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable("id") final Long id,
                             @NotNull @Valid @RequestBody RecipeRequest recipeRequest) {
        recipeService.updateRecipe(id, recipeContractMapper.recipeRequestToRecipeDto(recipeRequest));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Recipe",
            description = "This implementation deletes recipe",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Delete recipe request accepted and recipe deleted"),
                    @ApiResponse(responseCode = "500",
                            description = "Delete recipe service encountered an unexpected internal error"),
                    @ApiResponse(responseCode = "404",
                            description = "Delete recipe request accepted, but there is no recipe for requested id")
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable("id") final Long id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping
    @Operation(summary = "Get Recipes",
            description = "This implementation retrieves recipes by criteria",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Get recipes request accepted and recipe list is returned"),
                    @ApiResponse(responseCode = "500",
                            description = "Get recipes service encountered an unexpected internal error"),
                    @ApiResponse(responseCode = "400",
                            description = "Invalid request")
            })
    @ResponseStatus(HttpStatus.OK)
    public Page<RecipeResponse> getRecipes(
            @RequestParam(value = "dishType", required = false) final String dishType,
            @RequestParam(value = "dishTypeNot", required = false) final String dishTypeNot,
            @RequestParam(value = "ingredientInclude", required = false) final String ingredientInclude,
            @RequestParam(value = "ingredientNotInclude", required = false) final String ingredientNotInclude,
            @RequestParam(value = "maxPortionSize", required = false) final Integer maxPortionSize,
            @RequestParam(value = "minPortionSize", required = false) final Integer minPortionSize,
            @RequestParam(value = "portionSize", required = false) final Integer portionSize,
            @RequestParam(value = "cookingInstructionsLike", required = false) final String cookingInstructionsLike,
            @RequestParam(value = "cookingInstructionsNotLike", required = false) final String cookingInstructionsNotLike,
            Pageable pageable
    ) {
        RecipeSearchDto recipeSearchDto = RecipeSearchDto.builder().dishType(dishType).dishTypeNot(dishTypeNot).maxPortionSize(maxPortionSize)
                .minPortionSize(minPortionSize).portionSize(portionSize).cookingInstructionsLike(cookingInstructionsLike)
                .cookingInstructionsNotLike(cookingInstructionsNotLike).ingredientInclude(ingredientInclude).ingredientNotInclude(ingredientNotInclude).build();

        return new PageImpl<>(recipeService.getRecipes(recipeSearchDto, pageable).getContent().stream()
                .map(recipeContractMapper::recipeDtoToRecipeResponse).toList());
    }
}
