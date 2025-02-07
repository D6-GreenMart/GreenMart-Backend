package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CreateProductRequest;
import com.greenmart.app.domain.dtos.CreateProductRequestDto;
import com.greenmart.app.domain.dtos.ProductDto;
import com.greenmart.app.domain.entities.Category;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T19:18:46+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.vendorId( productVendorId( product ) );
        productDto.categoryId( productCategoryId( product ) );
        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.description( product.getDescription() );
        productDto.price( product.getPrice() );
        if ( product.getQuantity() != null ) {
            productDto.quantity( product.getQuantity().intValue() );
        }
        productDto.imagePath( product.getImagePath() );
        productDto.status( product.getStatus() );

        return productDto.build();
    }

    @Override
    public CreateProductRequest toCreateProductRequest(CreateProductRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        CreateProductRequest.CreateProductRequestBuilder createProductRequest = CreateProductRequest.builder();

        createProductRequest.name( dto.getName() );
        createProductRequest.description( dto.getDescription() );
        createProductRequest.price( dto.getPrice() );
        createProductRequest.quantity( dto.getQuantity() );
        createProductRequest.imagePath( dto.getImagePath() );
        createProductRequest.vendorId( dto.getVendorId() );
        createProductRequest.categoryId( dto.getCategoryId() );

        return createProductRequest.build();
    }

    private UUID productVendorId(Product product) {
        User vendor = product.getVendor();
        if ( vendor == null ) {
            return null;
        }
        return vendor.getId();
    }

    private UUID productCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }
}
