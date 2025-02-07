package com.greenmart.app.mappers;

import com.greenmart.app.domain.dtos.CartDto;
import com.greenmart.app.domain.dtos.CartItemDto;
import com.greenmart.app.domain.entities.Cart;
import com.greenmart.app.domain.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDto toDto(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    CartItemDto toDto(CartItem cartItem);

    List<CartItemDto> toCartItemDtos(List<CartItem> cartItems);
}
