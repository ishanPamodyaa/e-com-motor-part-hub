package edu.icet.dto;

import edu.icet.utility.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer id;
    private String orderNumber;
    private Double subTotal;
    private Double totalDiscount;
    private Double shippingCost;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private String notes;
    private OrderStatus status;
    private Integer userId;
    private Integer shippingAddressId;
    private Integer billingAddressId;
    private List<OrderItemDTO> items;
} 