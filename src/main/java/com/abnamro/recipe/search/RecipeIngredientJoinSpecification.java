package com.abnamro.recipe.search;

import com.abnamro.recipe.model.Ingredient;
import com.abnamro.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

public class RecipeIngredientJoinSpecification implements Specification<Recipe> {

    private static final String INGREDIENT_JOIN_PROPERTY_NAME = "ingredients";
    private static final String RECIPE_JOIN_PROPERTY_NAME = "recipe";
    private static final String ID_PROPERTY_NAME = "id";
    private final transient SearchCriteria searchCriteria;

    public RecipeIngredientJoinSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (Objects.isNull(searchCriteria) || Objects.isNull(searchCriteria.getValue())) {
            return criteriaBuilder.conjunction();
        }

        if (searchCriteria.getOperation() == SearchOperation.EQUAL) {
            return criteriaBuilder.equal(criteriaBuilder.lower(root.join(INGREDIENT_JOIN_PROPERTY_NAME, JoinType.LEFT).get(searchCriteria.getFilterKey())),
                    searchCriteria.getValue().getClass() == String.class ? searchCriteria.getValue().toString().toLowerCase() : searchCriteria.getValue());
        } else if (searchCriteria.getOperation() == SearchOperation.NOT_EQUAL) {
            Subquery<Ingredient> ingredientSubquery = query.subquery(Ingredient.class);
            Root<Ingredient> ingredientRoot = ingredientSubquery.from(Ingredient.class);
            ingredientSubquery.select(ingredientRoot)
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    criteriaBuilder.lower(ingredientRoot.get(searchCriteria.getFilterKey())),
                                    searchCriteria.getValue().getClass() == String.class ? searchCriteria.getValue().toString().toLowerCase() : searchCriteria.getValue()),
                            criteriaBuilder.equal(ingredientRoot.get(RECIPE_JOIN_PROPERTY_NAME).get(ID_PROPERTY_NAME), root.get(ID_PROPERTY_NAME))
                    ));
            return criteriaBuilder.not(criteriaBuilder.exists(ingredientSubquery));
        }

        return null;
    }
}
