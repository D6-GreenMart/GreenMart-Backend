package com.greenmart.app.services;

import java.util.List;
import java.util.UUID;

import com.greenmart.app.domain.entities.Category;

public interface CategoryService {
	List<Category> listCategories();
	Category createCategory(Category category);
	void deleteCategory(UUID id);
	
}
