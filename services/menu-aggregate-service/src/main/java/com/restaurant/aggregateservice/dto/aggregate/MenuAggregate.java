package com.restaurant.aggregateservice.dto.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.restaurant.aggregateservice.dto.menu.MenuItem;
import com.restaurant.aggregateservice.dto.review.ErrorResponse;
import com.restaurant.aggregateservice.dto.review.MenuRatingInfo;
import com.restaurant.aggregateservice.dto.review.Review;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuAggregate {
    private MenuItem menuItem;
    private List<Review> reviews;
    private MenuRatingInfo ratingInfo;
    private ErrorResponse errorResponse;
}
