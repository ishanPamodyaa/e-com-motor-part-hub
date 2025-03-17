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

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private Integer stockQuantity;

    private ProductCondition condition;

    @Size(max = 100)
    private String brand;

    @Size(max = 100)
    private String suitableModels;

    @Size(max = 20)
    private String year;

    @Size(max = 50)
    private String capacity;

    @Size(max = 15)
    private String contactNumber;

    private Long categoryId;
    
    private String categoryName;

    private List<String> imageUrls = new ArrayList<>();
    
    private String primaryImageUrl;

    private Set<String> tags;
} 