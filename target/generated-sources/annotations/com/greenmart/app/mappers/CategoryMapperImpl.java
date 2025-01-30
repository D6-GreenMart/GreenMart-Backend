package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CategoryDto;
import com.greenmart.app.domain.entities.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-30T22:27:29+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.productCount( calculateProductCount( category.getProducts() ) );
        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );
        categoryDto.description( category.getDescription() );
        categoryDto.imagePath( category.getImagePath() );

        return categoryDto.build();
    }
}
