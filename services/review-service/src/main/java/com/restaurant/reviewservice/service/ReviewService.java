package com.restaurant.reviewservice.service;

import com.restaurant.reviewservice.dto.CreateReviewRequest;
import com.restaurant.reviewservice.dto.RatedReviewsResponse;
import com.restaurant.reviewservice.dto.ReviewResponse;
import com.restaurant.reviewservice.dto.SortBy;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(CreateReviewRequest request, String username);

    ReviewResponse getReview(Long reviewId);

    List<ReviewResponse> getReviewsOfUser(String username, SortBy sort, int from, int size);

    RatedReviewsResponse getRatedReviewsForMenu(Long menuId, SortBy sort, int from, int size);
}
