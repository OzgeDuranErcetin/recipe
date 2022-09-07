package com.abnamro.recipe.search;

import com.abnamro.recipe.dto.RecipeSearchDto;
import com.abnamro.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

public interface RecipeSpecificationGenerator {
    Specification<Recipe> generate(RecipeSearchDto recipeSearchDto);
}
