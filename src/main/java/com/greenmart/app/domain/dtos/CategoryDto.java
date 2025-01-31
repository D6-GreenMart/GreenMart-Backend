package com.greenmart.app.domain.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	
	private UUID id;

	private String name;

	private String description;

	private String imagePath;
	
	private long productCount;
}
