package com.greenmart.app.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenmart.app.domain.dtos.CreateReviewRequestDto;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.Review;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.repositories.ProductRepository;
import com.greenmart.app.repositories.ReviewRepository;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.services.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;	
	private final ProductRepository productRepository;

	@Override
	@Transactional
	public Review createReview(UUID userId, CreateReviewRequestDto reviewRequestDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Product product = productRepository.findById(reviewRequestDto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Review review = Review.builder().user(user).product(product).rating(reviewRequestDto.getRating())
				.comment(reviewRequestDto.getComment()).build();

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getReviewsByProduct(UUID productId) {
		return reviewRepository.findByProductId(productId);
	}
}
