package edu.icet.dto;

import edu.icet.utility.ProductCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer stockQuantity;
    private String brand;
    private String suitableModels;
    private String year;
    private String capacity;
    private String contactNumber;
    private ProductCondition condition;
    private Integer categoryId;
    private Set<Integer> tagIds;
    private List<ProductImageDTO> images;
}