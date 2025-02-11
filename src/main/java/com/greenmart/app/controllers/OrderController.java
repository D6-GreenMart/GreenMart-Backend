package com.greenmart.app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.CreateOrderRequestDto;
import com.greenmart.app.domain.dtos.OrderDto;
import com.greenmart.app.domain.entities.User;
import com.greenmart.app.services.OrderService;
import com.greenmart.app.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateOrderRequestDto requestDto) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
    	return ResponseEntity.ok(orderService.createOrder(user.getId(),requestDto));
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@AuthenticationPrincipal UserDetails userDetails) {
    	User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(orderService.getOrdersByUserId(user.getId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{orderId}/status/delivered")
    public ResponseEntity<OrderDto> markOrderAsDelivered(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.markOrderAsDelivered(orderId));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<List<OrderDto>> getAllPendingOrders() {
        return ResponseEntity.ok(orderService.getAllPendingOrders());
    }

}

