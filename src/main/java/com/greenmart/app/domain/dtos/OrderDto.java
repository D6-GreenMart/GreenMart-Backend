package com.greenmart.app.domain.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.greenmart.app.domain.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private UUID id;
    private UUID userId;
    private String address;
    private double amount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;
}
