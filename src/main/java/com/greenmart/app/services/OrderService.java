package com.greenmart.app.services;

import java.util.List;
import java.util.UUID;

import com.greenmart.app.domain.dtos.CreateOrderRequestDto;
import com.greenmart.app.domain.dtos.OrderDto;

public interface OrderService {
    OrderDto createOrder(UUID userId, CreateOrderRequestDto requestDto);
    List<OrderDto> getOrdersByUserId(UUID userId);
    OrderDto getOrderById(UUID orderId);
    void cancelOrder(UUID orderId);
	OrderDto markOrderAsDelivered(UUID orderId);
	List<OrderDto> getAllPendingOrders();
}
