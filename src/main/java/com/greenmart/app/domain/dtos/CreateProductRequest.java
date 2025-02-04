package com.greenmart.app.domain.dtos;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {
	
	private String name;
    private String description;
    private Double price;
    private Long quantity;
    private String imagePath;
    private UUID vendorId;
    private UUID categoryId;
}
