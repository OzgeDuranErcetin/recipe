package com.abnamro.recipe.search;

import com.abnamro.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Objects;

public class RecipeSpecification implements Specification<Recipe> {
    private final transient SearchCriteria searchCriteria;

    public RecipeSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(searchCriteria) || Objects.isNull(searchCriteria.getValue())){
            return criteriaBuilder.conjunction();
        }
        return switch (searchCriteria.getOperation()) {
            case EQUAL -> criteriaBuilder.equal(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
                    searchCriteria.getValue().getClass() == String.class ? searchCriteria.getValue().toString().toLowerCase() : searchCriteria.getValue());
            case NOT_EQUAL -> criteriaBuilder.notEqual(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())),
                    searchCriteria.getValue().getClass() == String.class ? searchCriteria.getValue().toString().toLowerCase() : searchCriteria.getValue());
            case LIKE ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())), "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
            case NOT_LIKE ->
                    criteriaBuilder.notLike(criteriaBuilder.lower(root.get(searchCriteria.getFilterKey())), "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
            case GREATER_THAN_EQUAL ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL ->
                    criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
        };
    }
}
