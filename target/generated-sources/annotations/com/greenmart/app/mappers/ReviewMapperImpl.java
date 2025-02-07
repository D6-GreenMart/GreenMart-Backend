package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.ReviewDto;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.Review;
import com.greenmart.app.domain.entities.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-07T19:18:47+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDto toDto(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDto.ReviewDtoBuilder reviewDto = ReviewDto.builder();

        reviewDto.userId( reviewUserId( review ) );
        reviewDto.productId( reviewProductId( review ) );
        reviewDto.id( review.getId() );
        reviewDto.rating( review.getRating() );
        reviewDto.comment( review.getComment() );
        reviewDto.createdAt( review.getCreatedAt() );

        return reviewDto.build();
    }

    private UUID reviewUserId(Review review) {
        User user = review.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private UUID reviewProductId(Review review) {
        Product product = review.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }
}
