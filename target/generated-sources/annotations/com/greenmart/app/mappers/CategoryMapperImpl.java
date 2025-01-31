package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CategoryDto;
import com.greenmart.app.domain.dtos.CreateCategoryRequest;
import com.greenmart.app.domain.entities.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-31T11:35:16+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
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
        categoryDto.description( category.getDescription() );
        categoryDto.id( category.getId() );
        categoryDto.imagePath( category.getImagePath() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    @Override
    public Category toEntity(CreateCategoryRequest createCategoryRequest) {
        if ( createCategoryRequest == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.description( createCategoryRequest.getDescription() );
        category.imagePath( createCategoryRequest.getImagePath() );
        category.name( createCategoryRequest.getName() );

        return category.build();
    }
}
