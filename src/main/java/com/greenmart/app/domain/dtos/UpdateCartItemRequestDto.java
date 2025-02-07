package com.greenmart.app.domain.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @Min(0)
    private int quantity;
}
