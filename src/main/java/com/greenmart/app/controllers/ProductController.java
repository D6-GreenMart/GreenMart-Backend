package com.greenmart.app.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CreateProductRequest;
import com.greenmart.app.domain.dtos.CreateProductRequestDto;
import com.greenmart.app.domain.dtos.ProductDto;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.mappers.ProductMapper;
import com.greenmart.app.services.ProductService;
import com.greenmart.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	private final UserService userService;
	private final ProductMapper productMapper;

	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreateProductRequestDto createProductRequestDto) {
		User loggedInUser = userService.getUserByEmail(userDetails.getUsername());
	    
	    CreateProductRequest createProductRequest = productMapper.toCreateProductRequest(createProductRequestDto);
	    Product createdProduct = productService.createProduct(loggedInUser, createProductRequest);
	    ProductDto createdProductDto = productMapper.toDto(createdProduct);
	    
	    return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{productId}/status")
	public ResponseEntity<ProductDto> updateProductStatus(
	        @PathVariable UUID productId,
	        @RequestParam String status) {
	    productService.updateProductStatus(productId, status);
	    
	    // After updating the product, return the updated product details.
	    Product updatedProduct = productService.getProductById(productId);
	    ProductDto updatedProductDto = productMapper.toDto(updatedProduct);
	    
	    return ResponseEntity.ok(updatedProductDto);
	}
	
	@PutMapping("/{productId}/restock")
	public ResponseEntity<ProductDto> restockProduct(
	        @PathVariable UUID productId,
	        @RequestParam int quantity) {
	    productService.restockProduct(productId, quantity);
	    
	    // After restocking, return the updated product details.
	    Product updatedProduct = productService.getProductById(productId);
	    ProductDto updatedProductDto = productMapper.toDto(updatedProduct);
	    
	    return ResponseEntity.ok(updatedProductDto);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProductsById(@PathVariable UUID productId) {
	    Product product = productService.getProductById(productId);
	    return ResponseEntity.ok(productMapper.toDto(product));
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable UUID categoryId) {
	    List<Product> products = productService.getProductsByCategory(categoryId);
	    
	    List<ProductDto> productDtos = products.stream()
	            .map(productMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(productDtos);
	}
	
	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<List<ProductDto>> getProductsByVendor(@PathVariable UUID vendorId) {
	    List<Product> products = productService.getProductsByVendor(vendorId);
	    
	    List<ProductDto> productDtos = products.stream()
	            .map(productMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(productDtos);
	}
	
	@GetMapping("/vendor/{vendorId}/pending")
	public ResponseEntity<List<ProductDto>> getPendingProductsByVendor(@PathVariable UUID vendorId) {
	    List<Product> products = productService.getPendingProductsByVendor(vendorId);
	    
	    List<ProductDto> productDtos = products.stream()
	            .map(productMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(productDtos);
	}
	
	@GetMapping("/pending")
    public ResponseEntity<List<ProductDto>> getPendingProducts() {
        List<Product> pendingProducts = productService.getPendingProducts();
        
        List<ProductDto> productDtos = pendingProducts.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(productDtos);
    }
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
	    List<Product> products = productService.searchProducts(keyword);
	    
	    List<ProductDto> productDtos = products.stream()
	            .map(productMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(productDtos);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts() {
	    List<Product> products = productService.getAllProducts();
	    
	    List<ProductDto> productDtos = products.stream()
	            .map(productMapper::toDto)
	            .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(productDtos);
	}
	


}
