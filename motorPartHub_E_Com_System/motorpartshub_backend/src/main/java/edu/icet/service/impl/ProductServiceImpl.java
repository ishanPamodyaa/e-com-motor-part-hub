package edu.icet.service.impl;

import edu.icet.dto.ProductDTO;
import edu.icet.dto.ProductImageDTO;
import edu.icet.entity.Category;
import edu.icet.entity.Product;
import edu.icet.entity.ProductImage;
import edu.icet.entity.Tag;
import edu.icet.repository.CategoryRepository;
import edu.icet.repository.ProductImageRepository;
import edu.icet.repository.ProductRepository;
import edu.icet.repository.TagRepository;
import edu.icet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;
    final ProductImageRepository productImageRepository;
    final TagRepository tagRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {

        Product product =convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        List<ProductImage> productImages = new ArrayList<>();

        for(ProductImageDTO productImageDTO : productDTO.getImages()){
            ProductImage productImage = new ProductImage();

            productImage.setImageUrl(productImageDTO.getImageUrl());
            productImage.setPrimary(productImageDTO.isPrimary());
            productImage.setProduct(savedProduct);
            productImageRepository.save(productImage);
            productImages.add(productImage);
        }
        savedProduct.setImages(productImages);
        return convertToDTO(savedProduct);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setBrand(productDTO.getBrand());
        product.setSuitableModels(productDTO.getSuitableModels());
        product.setYear(productDTO.getYear());
        product.setCapacity(productDTO.getCapacity());
        product.setContactNumber(productDTO.getContactNumber());
        product.setCondition(productDTO.getCondition());

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        Set<Tag> tags = productDTO.getTagIds().stream()
                .map(id -> tagRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toSet());
        product.setTags(tags);

        return product;
    }
    public ProductDTO convertToDTO(Product product) {
        List<ProductImageDTO> imageDTOs = product.getImages().stream()
                .map(image -> new ProductImageDTO(image.getImageUrl(), image.isPrimary()))
                .collect(Collectors.toList());

        return new ProductDTO(
                product.getName(), product.getDescription(), product.getPrice(),
                product.getDiscount(), product.getStockQuantity(), product.getBrand(),
                product.getSuitableModels(), product.getYear(), product.getCapacity(),
                product.getContactNumber(), product.getCondition(),
                product.getCategory().getId(),
                product.getTags().stream().map(Tag::getId).collect(Collectors.toSet()),
                imageDTOs
        );
    }


}