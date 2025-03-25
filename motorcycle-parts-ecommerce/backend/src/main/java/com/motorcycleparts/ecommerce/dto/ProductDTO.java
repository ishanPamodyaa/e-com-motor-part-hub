package com.motorcycleparts.ecommerce.dto;

import com.motorcycleparts.ecommerce.models.Product.ProductCondition;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private ProductCondition condition;
    private String brand;
    private String suitableModels;
    private String year;
    private String capacity;
    private String contactNumber;
    private Long categoryId;
    private String categoryName;
    private List<String> imageUrls = new ArrayList<>();
    private String primaryImageUrl;
    private Set<String> tags;
} 