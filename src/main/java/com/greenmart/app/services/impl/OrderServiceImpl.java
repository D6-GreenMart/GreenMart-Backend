package com.greenmart.app.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenmart.app.domain.OrderStatus;
import com.greenmart.app.domain.dtos.CreateOrderRequestDto;
import com.greenmart.app.domain.dtos.OrderDto;
import com.greenmart.app.domain.entities.Cart;
import com.greenmart.app.domain.entities.CartItem;
import com.greenmart.app.domain.entities.Order;
import com.greenmart.app.domain.entities.OrderItem;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.mappers.OrderMapper;
import com.greenmart.app.repositories.CartItemRepository;
import com.greenmart.app.repositories.CartRepository;
import com.greenmart.app.repositories.OrderItemRepository;
import com.greenmart.app.repositories.OrderRepository;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.services.OrderService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(CreateOrderRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .address(requestDto.getAddress())
                .amount(cart.getCartItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                        .sum())
                .status(OrderStatus.PENDING)
                .orderItems(new HashSet<>())
                .build();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();
            order.getOrderItems().add(orderItem);
        }

        order = orderRepository.save(order);
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(UUID userId) {
        return orderMapper.toDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a completed order");
        }

        orderRepository.delete(order);
    }
    
    @Override
    @Transactional
    public OrderDto markOrderAsDelivered(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order is already completed");
        }

        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        
        return orderMapper.toDto(order);
    }

}
