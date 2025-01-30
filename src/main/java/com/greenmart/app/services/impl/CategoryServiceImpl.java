package com.greenmart.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.repositories.CategoryRepository;
import com.greenmart.app.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	
	@Override
	public List<Category> listCategories() {
		return categoryRepository.findAllWithProductCount();
	}

}
