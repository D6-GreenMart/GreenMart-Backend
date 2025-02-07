package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CategoryDto;
import com.greenmart.app.domain.dtos.CreateCategoryRequest;
import com.greenmart.app.domain.entities.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T19:18:47+0530",
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

    @Override
    public Category toEntity(CreateCategoryRequest createCategoryRequest) {
        if ( createCategoryRequest == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( createCategoryRequest.getName() );
        category.description( createCategoryRequest.getDescription() );
        category.imagePath( createCategoryRequest.getImagePath() );

        return category.build();
    }
}
