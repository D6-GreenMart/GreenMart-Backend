package com.greenmart.app.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.greenmart.app.domain.dtos.OrderDto;
import com.greenmart.app.domain.dtos.OrderItemDto;
import com.greenmart.app.domain.entities.Order;
import com.greenmart.app.domain.entities.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "orderItems", ignore = true)
    Order toEntity(OrderDto orderDto);

    default OrderItemDto mapOrderItem(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    default List<OrderItemDto> mapOrderItems(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapOrderItem)
                .toList();
    }
}

