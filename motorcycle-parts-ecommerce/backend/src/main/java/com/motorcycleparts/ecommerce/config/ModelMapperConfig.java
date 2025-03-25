package com.motorcycleparts.ecommerce.config;

import com.motorcycleparts.ecommerce.dto.CategoryDTO;
import com.motorcycleparts.ecommerce.dto.ProductDTO;
import com.motorcycleparts.ecommerce.models.Category;
import com.motorcycleparts.ecommerce.models.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Skip parentId and parentName when mapping from CategoryDTO to Category
        PropertyMap<CategoryDTO, Category> categoryMap = new PropertyMap<CategoryDTO, Category>() {
            protected void configure() {
                skip().setParent(null);
            }
        };
        
        // Skip categoryId and categoryName when mapping from ProductDTO to Product
        PropertyMap<ProductDTO, Product> productMap = new PropertyMap<ProductDTO, Product>() {
            protected void configure() {
                skip().setCategory(null);
            }
        };
        
        modelMapper.addMappings(categoryMap);
        modelMapper.addMappings(productMap);
        
        return modelMapper;
    }
} 