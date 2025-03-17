package com.motorcycleparts.ecommerce.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class AddressDTO {
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String streetAddress;
    
    @NotBlank
    @Size(max = 100)
    private String city;
    
    @NotBlank
    @Size(max = 100)
    private String state;
    
    @NotBlank
    @Size(max = 20)
    private String postalCode;
    
    @NotBlank
    @Size(max = 100)
    private String country;
    
    private boolean isDefault;
    
    private Long userId;
} 