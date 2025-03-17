package com.motorcycleparts.ecommerce.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    @NotBlank
    private String paymentMethodId;
    
    @NotNull
    private BigDecimal amount;
    
    @NotBlank
    private String currency;
    
    @NotBlank
    private String description;
    
    private Long orderId;
} 