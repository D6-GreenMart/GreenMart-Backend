package com.greenmart.app.domain.dtos;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.greenmart.app.domain.ProductStatus;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

	private UUID id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private String imagePath;
	private UUID vendorId;
	private UUID categoryId;
}
