package com.greenmart.app.domain.dtos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequestDto {

	@NotBlank(message="Product name is required")
	@Size(min = 3, max = 200, message = "Name must be between {min} and {max} characters")
	private String name;

	@NotNull(message = "Category is required")
	private UUID categoryId;

	private String description;

	@NotNull(message = "Price is required")
	private Double price;

	@NotNull(message = "Quantity is required")
	private Long quantity;

	private String imagePath;
}
