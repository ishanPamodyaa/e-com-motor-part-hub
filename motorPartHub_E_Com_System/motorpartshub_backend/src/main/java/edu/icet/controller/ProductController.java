package edu.icet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.dto.ProductDTO;
import edu.icet.dto.ProductImageDTO;
import edu.icet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    final ProductService productService;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("product") String productJson) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            if (productDTO.getName() == null || productDTO.getCategoryId() == null) {
                return ResponseEntity.badRequest().body("Invalid product data");
            }

            List<ProductImageDTO> imageDTOS = images.stream()
                    .map(image -> new ProductImageDTO(image.getOriginalFilename(), false))
                    .collect(Collectors.toList());
            if (!imageDTOS.isEmpty()) {
                imageDTOS.get(0).setPrimary(true);
            }
            productDTO.setImages(imageDTOS);

            ProductDTO createdProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(createdProduct);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

} 