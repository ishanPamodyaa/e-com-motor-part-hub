package edu.icet.dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Integer id;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double totalPrice;
    private Integer productId;
} 