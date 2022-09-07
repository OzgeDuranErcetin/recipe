package com.abnamro.recipe.api;

import com.abnamro.recipe.IntegrationTest;
import com.abnamro.recipe.model.Recipe;
import com.abnamro.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static com.abnamro.recipe.RecipeFixture.*;
import static com.abnamro.recipe.TestConstant.VEGETARIAN_DISH_TYPE;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecipeIntegrationTest extends IntegrationTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    void whenRecipeCreate_thenReturnCreatedRecipe() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", greaterThan(0)));
    }

    @Test
    void whenRecipeCreateWithInvalidDishType_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"xxxx\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithPortionSizeIsZero_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 0," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithTooLongDishName_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithTooLongDishType_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithTooLongIngredientName_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithMissingContent_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeCreateWithSomeOfContentsMissing_thenBadRequest() throws Exception {
        mvc.perform(
                        post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"something missing\"}")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRecipeUpdate_thenNoContent() throws Exception {
        Recipe recipe = recipeRepository.save(vegetarianRecipe());

        mvc.perform(
                        put("/recipe/"+ recipe.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void whenNonExistingRecipeUpdate_thenNotFound() throws Exception {
        mvc.perform(
                        put("/recipe/5235")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"dishName\":\"Onion Soup\"," +
                                        "\"dishType\":\"Vegetarian\"," +
                                        "\"portionSize\": 4," +
                                        "\"cookingInstructions\":\"cut onions, heat the water, cook them together in pot\"," +
                                        "\"ingredients\": [{\"name\": \"onion\"}, {\"name\": \"water\"}, {\"name\": \"salt\"}]" +
                                        "}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void whenNonExistingRecipeDelete_thenNotFound() throws Exception {
        mvc.perform(
                        delete("/recipe/352")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void whenExistingRecipeDelete_thenNoContent() throws Exception {
        Recipe recipe = recipeRepository.save(vegetarianRecipe());

        mvc.perform(
                        delete("/recipe/" + recipe.getId())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void whenRetrieveVegetarianRecipes_thenReturnAllVegetarianRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());

        mvc.perform(
                        get("/recipe?dishType=vegetarian")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishType",  equalTo(VEGETARIAN_DISH_TYPE)));
    }

    @Test
    void whenRetrieveNotVegetarianRecipes_thenReturnNotVegetarianRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?dishTypeNot=vegetarian")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(2)));
    }

    @Test
    void whenRetrieveIncludeWaterRecipes_thenReturnIncludedWaterRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?ingredientInclude=Water")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Pasta")));
    }

    @Test
    void whenRetrieveNotIncludeWaterRecipes_thenReturnNotIncludedWaterRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?ingredientNotInclude=Tomato")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Pasta")));
    }

    @Test
    void whenRetrieveMaxPortionSizeFourRecipes_thenReturnMaxFourPeopleRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?maxPortionSize=4")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(2)));
    }

    @Test
    void whenRetrieveMinPortionSizeSixRecipes_thenReturnMinSixPeopleRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?minPortionSize=6")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Lasagna")));
    }

    @Test
    void whenRetrievePortionSizeOneRecipes_thenReturnOnePeopleRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?portionSize=1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Salad")));
    }

    @Test
    void whenRetrieveCookingInstructionHasOvenRecipes_thenReturnCookingInstructionHasOvenRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?cookingInstructionsLike=Oven")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Lasagna")));
    }

    @Test
    void whenRetrieveCookingInstructionHasNotOvenRecipes_thenReturnCookingInstructionHasNotOvenRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?cookingInstructionsNotLike=Oven")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",  equalTo(2)));
    }

    @Test
    void whenRetrieveForFourPeopleAndWithPotatoesRecipes_thenReturnForFourPeopleAndWithPotatoesRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?ingredientInclude=potatoes&portionSize=4")
                )
                .andExpect(status().isOk());
    }

    @Test
    void whenRetrieveWithoutSalmonAndInstructorHasOvenRecipes_thenReturnWithoutSalmonAndInstructorHasOvenRecipes() throws Exception {
        recipeRepository.save(vegetarianRecipe());
        recipeRepository.save(veganRecipe());
        recipeRepository.save(meatRecipe());

        mvc.perform(
                        get("/recipe?ingredientNotInclude=salmon&cookingInstructionsLike=oven")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()",  equalTo(1)))
                .andExpect(jsonPath("$.content[0].dishName", equalTo("Lasagna")));
    }
}
