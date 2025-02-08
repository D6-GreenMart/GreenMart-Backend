package com.greenmart.app.services;

import java.util.List;
import java.util.UUID;

import com.greenmart.app.domain.dtos.CreateReviewRequestDto;
import com.greenmart.app.domain.entities.Review;

public interface ReviewService {
    Review createReview(UUID userId, CreateReviewRequestDto reviewRequestDto);
    List<Review> getReviewsByProduct(UUID productId);
}
