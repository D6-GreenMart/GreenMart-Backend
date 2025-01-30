package com.greenmart.app.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.greenmart.app.domain.dtos.CategoryDto;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.domain.entities.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

	@Mapping(target = "productCount", source = "products", qualifiedByName = "calculateProductCount")
	CategoryDto toDto(Category category);
	
	@Named("calculateProductCount")
	default long calculateProductCount(List<Product> products) {
		if(null == products)
		{
			return 0;
		}
		return products.stream().count();
	}
	
	
}
