package com.greenmart.app.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenmart.app.domain.ProductStatus;
import com.greenmart.app.domain.dtos.CreateProductRequest;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.repositories.CategoryRepository;
import com.greenmart.app.repositories.ProductRepository;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.services.CategoryService;
import com.greenmart.app.services.ProductService;
import com.greenmart.app.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public Product createProduct(User user, CreateProductRequest createProductRequest) {
        Category category = categoryService.getCategoryById(createProductRequest.getCategoryId());
        Product newProduct = new Product();
        newProduct.setName(createProductRequest.getName());
        newProduct.setDescription(createProductRequest.getDescription());
        newProduct.setPrice(createProductRequest.getPrice());
        newProduct.setQuantity(createProductRequest.getQuantity());
        newProduct.setImagePath(createProductRequest.getImagePath());
        newProduct.setCategory(category);  // Setting category
        newProduct.setVendor(user);  
        return productRepository.save(newProduct);
    }
    
    @Override
    public void updateProductStatus(UUID productId, String status) {
    	ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        
        // Assuming the Product class has a status field.
        product.setStatus(productStatus);
        
        productRepository.save(product);
    }

	@Override
	public Product getProductById(UUID productId) {
		return productRepository.findById(productId)
				.orElseThrow(()->new EntityNotFoundException("Product not found for id "+productId));
	}
	
	@Override
	public void restockProduct(UUID productId, int quantityToAdd) {
	    Product product = productRepository.findById(productId)
	        .orElseThrow(() -> new EntityNotFoundException("Product not found for id "+productId));

	    // Add the new quantity to the existing stock
	    product.setQuantity(product.getQuantity() + quantityToAdd);
	    
	    productRepository.save(product);
	}
	
	@Override
	public List<Product> getProductsByCategory(UUID categoryId) {
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
	    return productRepository.findByCategoryAndStatus(category, ProductStatus.APPROVED);
	}
	
	@Override
	public List<Product> getProductsByVendor(UUID vendorId) {
	    User vendor = userRepository.findById(vendorId)
	            .orElseThrow(() -> new EntityNotFoundException("Vendor not found"));
	    return productRepository.findByVendorAndStatus(vendor, ProductStatus.APPROVED);
	}
	
	@Override
	public List<Product> getPendingProducts() {
	    return productRepository.findAllByStatus(ProductStatus.PENDING);
	}


    
    


	
}
