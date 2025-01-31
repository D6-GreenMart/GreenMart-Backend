package com.greenmart.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CategoryDto;
import com.greenmart.app.domain.dtos.CreateCategoryRequest;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.mappers.CategoryMapper;
import com.greenmart.app.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryMapper categoryMapper;

	@GetMapping
	public ResponseEntity<List<CategoryDto>> listCategories() {
		List<CategoryDto> categories = categoryService.listCategories().stream()
				.map(category -> categoryMapper.toDto(category)).toList();
		return ResponseEntity.ok(categories);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
		Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
		Category savedCategory = categoryService.createCategory(categoryToCreate);
		return new ResponseEntity<>(
				categoryMapper.toDto(savedCategory),
				HttpStatus.CREATED		
		);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				
	}
}
