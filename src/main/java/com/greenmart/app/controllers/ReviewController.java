package com.greenmart.app.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CreateReviewRequestDto;
import com.greenmart.app.domain.dtos.ReviewDto;
import com.greenmart.app.domain.entities.Review;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.mappers.ReviewMapper;
import com.greenmart.app.services.ReviewService;
import com.greenmart.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    // Create a new review
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
    		@AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReviewRequestDto reviewRequestDto) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
        Review createdReview = reviewService.createReview(user.getId(), reviewRequestDto);
        return ResponseEntity.ok(reviewMapper.toDto(createdReview));
    }

    // Get all reviews for a specific product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable UUID productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(reviewDtos);
    }
}
