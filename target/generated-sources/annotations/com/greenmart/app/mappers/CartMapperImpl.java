package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.CartItemDto;
import com.greenmart.app.domain.entities.Cart;
import com.greenmart.app.domain.entities.CartItem;
import com.greenmart.app.domain.entities.Product;
import com.greenmart.app.domain.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-11T08:49:34+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public CartDto toDto(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDto cartDto = new CartDto();

        cartDto.setUserId( cartUserId( cart ) );
        cartDto.setCartItems( toCartItemDtos( cart.getCartItems() ) );
        cartDto.setId( cart.getId() );

        return cartDto;
    }

    @Override
    public CartItemDto toDto(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDto cartItemDto = new CartItemDto();

        cartItemDto.setProductId( cartItemProductId( cartItem ) );
        cartItemDto.setProductName( cartItemProductName( cartItem ) );
        cartItemDto.setId( cartItem.getId() );
        cartItemDto.setQuantity( cartItem.getQuantity() );

        return cartItemDto;
    }

    @Override
    public List<CartItemDto> toCartItemDtos(List<CartItem> cartItems) {
        if ( cartItems == null ) {
            return null;
        }

        List<CartItemDto> list = new ArrayList<CartItemDto>( cartItems.size() );
        for ( CartItem cartItem : cartItems ) {
            list.add( toDto( cartItem ) );
        }

        return list;
    }

    private UUID cartUserId(Cart cart) {
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private UUID cartItemProductId(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String cartItemProductName(CartItem cartItem) {
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }
}
