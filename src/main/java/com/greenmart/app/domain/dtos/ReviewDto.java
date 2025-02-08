package com.greenmart.app.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
	private UUID id;
	private UUID userId;
	private UUID productId;
	private int rating;
	private String comment;
	private LocalDateTime createdAt;
}
