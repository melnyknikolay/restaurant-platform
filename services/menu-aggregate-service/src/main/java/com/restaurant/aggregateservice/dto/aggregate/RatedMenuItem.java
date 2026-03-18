package com.restaurant.aggregateservice.dto.aggregate;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.restaurant.aggregateservice.dto.menu.Category;
import com.restaurant.aggregateservice.dto.menu.IngredientCollection;
import com.restaurant.aggregateservice.dto.menu.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RatedMenuItem extends MenuItem {
    private Float wilsonScore;
    private Float avgStars;

    @Builder
    public RatedMenuItem(Long id,
                         String name,
                         String description,
                         BigDecimal price,
                         Category category,
                         long timeToCook,
                         double weight,
                         String imageUrl,
                         LocalDateTime updatedAt,
                         LocalDateTime createdAt,
                         IngredientCollection ingredientCollection,
                         Float wilsonScore,
                         Float avgStars) {
        super(id, name, description, price, category, timeToCook, weight, imageUrl, updatedAt, createdAt, ingredientCollection);
        this.wilsonScore = wilsonScore;
        this.avgStars = avgStars;
    }
}
