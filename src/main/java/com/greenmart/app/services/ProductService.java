package com.greenmart.app.services;

import java.util.List;
import java.util.UUID;

import com.greenmart.app.domain.dtos.CreateProductRequest;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;

public interface ProductService {


	Product createProduct(User user, CreateProductRequest createProductRequest);

	void updateProductStatus(UUID productId, String status);

	Product getProductById(UUID productId);

	void restockProduct(UUID productId, int quantityToAdd);

	List<Product> getProductsByCategory(UUID categoryId);

	List<Product> getProductsByVendor(UUID vendorId);

	List<Product> getPendingProducts();

	List<Product> getPendingProductsByVendor(UUID vendorId);

	List<Product> searchProducts(String keyword);

	List<Product> getAllProducts();
	
	
}
