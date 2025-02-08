package com.greenmart.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.greenmart.app.domain.dtos.CreateProductRequest;
import com.greenmart.app.domain.dtos.CreateProductRequestDto;
import com.greenmart.app.domain.dtos.ProductDto;
import com.greenmart.app.domain.entities.Product;

@Mapper(componentModel="spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
	
	@Mapping(source = "vendor.id", target = "vendorId") // Maps vendor's id to vendorId in ProductDto
    @Mapping(source = "category.id", target = "categoryId")
	ProductDto toDto(Product product);
	
	CreateProductRequest toCreateProductRequest(CreateProductRequestDto dto);
}
