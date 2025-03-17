package com.motorcycleparts.ecommerce.dto;

import com.motorcycleparts.ecommerce.models.Order.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String userEmail;
    private List<OrderItemDTO> items = new ArrayList<>();
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal total;
    private OrderStatus status;
    private String paymentMethod;
    private String paymentId;
    private LocalDateTime orderDate;
    private LocalDateTime lastUpdated;
    private String notes;
    
    @Data
    public static class OrderItemDTO {
        private Long id;
        private Long productId;
        private String productName;
        private String productImage;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
    }
    
    @Data
    public static class AddressDTO {
        private Long id;
        private String streetAddress;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private boolean isDefault;
    }
} 