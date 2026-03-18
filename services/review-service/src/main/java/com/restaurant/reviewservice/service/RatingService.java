package com.restaurant.reviewservice.service;

import com.restaurant.reviewservice.dto.GetRatingsRequest;
import com.restaurant.reviewservice.dto.RatingsResponse;
import com.restaurant.reviewservice.storage.model.MenuRatingInfo;

public interface RatingService {

    void saveRating(Long menuId, Integer rate);

    MenuRatingInfo getRatingOfMenu(Long menuId);

    RatingsResponse getRatingsOfMenus(GetRatingsRequest request);
}
