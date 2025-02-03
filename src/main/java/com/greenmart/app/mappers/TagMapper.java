package com.greenmart.app.mappers;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.greenmart.app.domain.ProductStatus;
import com.greenmart.app.domain.dtos.TagResponse;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.Tag;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TagMapper {
	
	@Mapping(target = "productCount", source = "products", qualifiedByName = "calculateProductCount")
	TagResponse toTagResponse(Tag tag);
	
	@Named("calculateProductCount")
	default Integer calculateProductCount(Set<Product> products) {
		if(products==null) {
			return 0;
		}
		return (int) products.stream().filter(product -> ProductStatus.APPROVED.equals(product.getStatus())).count();
	}
}
