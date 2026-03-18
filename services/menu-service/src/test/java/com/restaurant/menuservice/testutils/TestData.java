package com.restaurant.menuservice.testutils;

import com.restaurant.menuservice.dto.CreateMenuRequest;
import com.restaurant.menuservice.dto.UpdateMenuRequest;
import com.restaurant.menuservice.storage.model.Category;
import com.restaurant.menuservice.storage.model.Ingredient;
import com.restaurant.menuservice.storage.model.IngredientCollection;

import java.math.BigDecimal;
import java.util.List;

import static com.restaurant.menuservice.testutils.TestConstants.*;


public class TestData {

    public static IngredientCollection italianSaladIngredients() {
        return new IngredientCollection(
                List.of(
                        new Ingredient(ITALIAN_SALAD_GREENS_INGREDIENT, ITALIAN_SALAD_GREENS_INGREDIENT_CALORIES),
                        new Ingredient(ITALIAN_SALAD_TOMATOES_INGREDIENT, ITALIAN_SALAD_TOMATOES_INGREDIENT_CALORIES)
                )
        );
    }

    public static UpdateMenuRequest updateMenuFullRequest() {
        return UpdateMenuRequest.builder()
                .name("New Cappuccino")
                .price(BigDecimal.valueOf(100.01))
                .timeToCook(2000L)
                .description("New Cappuccino Description")
                .imageUrl("http://images.com/new_cappuccino.png")
                .build();
    }

    public static CreateMenuRequest createMenuRequest() {
        return CreateMenuRequest.builder()
                .name(ITALIAN_SALAD_NAME)
                .description(ITALIAN_SALAD_DESCRIPTION)
                .price(ITALIAN_SALAD_PRICE)
                .category(Category.SALADS)
                .timeToCook(ITALIAN_SALAD_TIME_TO_COOK)
                .weight(ITALIAN_SALAD_WEIGHT)
                .imageUrl(ITALIAN_SALAD_IMAGE_URL)
                .ingredientCollection(italianSaladIngredients())
                .build();
    }
}
