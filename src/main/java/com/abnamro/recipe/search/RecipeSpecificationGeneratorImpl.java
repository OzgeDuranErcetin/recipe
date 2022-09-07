package com.abnamro.recipe.search;

import com.abnamro.recipe.dto.RecipeSearchDto;
import com.abnamro.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RecipeSpecificationGeneratorImpl implements RecipeSpecificationGenerator{

    private static final String DISH_TYPE_PROPERTY_NAME = "dishType";
    private static final String PORTION_SIZE_PROPERTY_NAME = "portionSize";
    private static final String COOKING_INSTRUCTIONS_PROPERTY_NAME = "cookingInstructions";
    private static final String INGREDIENT_NAME_PROPERTY_NAME = "name";

    public Specification<Recipe> generate(RecipeSearchDto recipeSearchDto) {
        return generateDishTypeSpecification(recipeSearchDto.getDishType())
                .and(generateDishTypeNotIncludeSpecification(recipeSearchDto.getDishTypeNot()))
                .and(generatePortionSizeSpecification(recipeSearchDto.getPortionSize()))
                .and(generateMaxPortionSizeSpecification(recipeSearchDto.getMaxPortionSize()))
                .and(generateMinPortionSizeSpecification(recipeSearchDto.getMinPortionSize()))
                .and(generateCookingInstructionsLikeSpecification(recipeSearchDto.getCookingInstructionsLike()))
                .and(generateCookingInstructionsNotLikeSpecification(recipeSearchDto.getCookingInstructionsNotLike()))
                .and(generateIngredientIncludeSpecification(recipeSearchDto.getIngredientInclude()))
                .and(generateIngredientNotIncludeSpecification(recipeSearchDto.getIngredientNotInclude()));
    }

    private Specification<Recipe> generateDishTypeSpecification(String dishType) {
        return new RecipeSpecification(new SearchCriteria(DISH_TYPE_PROPERTY_NAME, SearchOperation.EQUAL, dishType));
    }

    private Specification<Recipe> generateDishTypeNotIncludeSpecification(String dishType) {
        return new RecipeSpecification(new SearchCriteria(DISH_TYPE_PROPERTY_NAME, SearchOperation.NOT_EQUAL, dishType));
    }

    private Specification<Recipe> generatePortionSizeSpecification(Integer portionSize) {
        return new RecipeSpecification(new SearchCriteria(PORTION_SIZE_PROPERTY_NAME, SearchOperation.EQUAL, portionSize));
    }

    private Specification<Recipe> generateMaxPortionSizeSpecification(Integer maxPortionSize) {
        return new RecipeSpecification(new SearchCriteria(PORTION_SIZE_PROPERTY_NAME, SearchOperation.LESS_THAN_EQUAL, maxPortionSize));
    }

    private Specification<Recipe> generateMinPortionSizeSpecification(Integer minPortionSize) {
        return new RecipeSpecification(new SearchCriteria(PORTION_SIZE_PROPERTY_NAME, SearchOperation.GREATER_THAN_EQUAL, minPortionSize));
    }

    private Specification<Recipe> generateCookingInstructionsLikeSpecification(String cookingInstructionsLike) {
        return new RecipeSpecification(new SearchCriteria(COOKING_INSTRUCTIONS_PROPERTY_NAME, SearchOperation.LIKE, cookingInstructionsLike));
    }

    private Specification<Recipe> generateCookingInstructionsNotLikeSpecification(String cookingInstructionsNotLike) {
        return new RecipeSpecification(new SearchCriteria(COOKING_INSTRUCTIONS_PROPERTY_NAME, SearchOperation.NOT_LIKE, cookingInstructionsNotLike));
    }

    private Specification<Recipe> generateIngredientIncludeSpecification(String ingredientInclude) {
        return new RecipeIngredientJoinSpecification(new SearchCriteria(INGREDIENT_NAME_PROPERTY_NAME, SearchOperation.EQUAL, ingredientInclude));
    }

    private Specification<Recipe> generateIngredientNotIncludeSpecification(String ingredientNotInclude) {
        return new RecipeIngredientJoinSpecification(new SearchCriteria(INGREDIENT_NAME_PROPERTY_NAME, SearchOperation.NOT_EQUAL, ingredientNotInclude));
    }
}
