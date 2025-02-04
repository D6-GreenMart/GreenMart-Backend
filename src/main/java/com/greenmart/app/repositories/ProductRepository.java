package com.greenmart.app.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenmart.app.domain.ProductStatus;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;

//@Repository
//public interface ProductRepository extends JpaRepository<Product, UUID> {
//	List<Product> findAllByStatusAndCategoryAndTagsContaining(ProductStatus status, Category category, Tag tag);
//	List<Product> findAllByStatusAndCategory(ProductStatus status, Category category);
//	List<Product> findAllByStatusAndTagsContaining(ProductStatus status, Tag tag);
//	List<Product> findAllByStatus(ProductStatus status);
//	List<Product> findAllByVendorAndStatus(Vendor vendor, ProductStatus status);
//}


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByVendor(User vendor);

    List<Product> findAllByRestockedAtOrderByRestockedAtDesc(LocalDateTime restockedAt);

    List<Product> findAllByStatus(ProductStatus status);

	List<Product> findByCategoryAndStatus(Category category, ProductStatus approved);

	List<Product> findByVendorAndStatus(User vendor, ProductStatus approved);
	
	List<Product> findByStatus(ProductStatus status);
}