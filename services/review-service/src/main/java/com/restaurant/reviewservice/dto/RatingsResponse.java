package com.restaurant.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.restaurant.reviewservice.storage.model.MenuRatingInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingsResponse {
    private List<MenuRatingInfo> menuRatings;
}
