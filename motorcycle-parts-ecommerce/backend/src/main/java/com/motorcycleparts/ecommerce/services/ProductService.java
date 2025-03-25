package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.dto.ProductDTO;
import com.motorcycleparts.ecommerce.exception.ResourceNotFoundException;
import com.motorcycleparts.ecommerce.models.Category;
import com.motorcycleparts.ecommerce.models.Product;
import com.motorcycleparts.ecommerce.models.Product.ProductCondition;
import com.motorcycleparts.ecommerce.models.ProductImage;
import com.motorcycleparts.ecommerce.models.Tag;
import com.motorcycleparts.ecommerce.repositories.CategoryRepository;
import com.motorcycleparts.ecommerce.repositories.ProductRepository;
import com.motorcycleparts.ecommerce.repositories.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getInStockProducts(Pageable pageable) {
        Page<Product> products = productRepository.findInStockProducts(pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsByCondition(ProductCondition condition, Pageable pageable) {
        Page<Product> products = productRepository.findByCondition(condition, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsByCategoryAndCondition(Long categoryId, ProductCondition condition, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Page<Product> products = productRepository.findByCategoryAndCondition(category, condition, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Page<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        return products.map(this::convertToDTO);
    }

    public Page<ProductDTO> searchProducts(String keyword, Pageable pageable) {
        Page<Product> products = productRepository.searchProducts(keyword, pageable);
        return products.map(this::convertToDTO);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToDTO(product);
    }

    public List<ProductDTO> getSimilarProducts(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<Product> similarProducts = productRepository.findSimilarProducts(product.getCategory().getId(), productId);
        return similarProducts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, List<MultipartFile> images) {
        Product product = convertToEntity(productDTO);
        
        // Set category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        
        // Handle tags - find existing or create new ones
        if (productDTO.getTags() != null && !productDTO.getTags().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : productDTO.getTags()) {
                // Try to find an existing tag
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            // Create and save a new tag if it doesn't exist
                            Tag newTag = new Tag(tagName);
                            return tagRepository.save(newTag);
                        });
                tags.add(tag);
            }
            product.setTags(tags);
        }
        
        Product savedProduct = productRepository.save(product);
        
        // Handle images
        if (images != null && !images.isEmpty()) {
            List<ProductImage> productImages = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                String fileName = saveImage(image, savedProduct.getId());
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(fileName);
                productImage.setPrimary(i == 0); // First image is primary
                productImage.setProduct(savedProduct);
                productImages.add(productImage);
            }
            savedProduct.setImages(productImages);
            savedProduct = productRepository.save(savedProduct);
        }
        
        return convertToDTO(savedProduct);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());
        existingProduct.setCondition(productDTO.getCondition());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setSuitableModels(productDTO.getSuitableModels());
        existingProduct.setYear(productDTO.getYear());
        existingProduct.setCapacity(productDTO.getCapacity());
        existingProduct.setContactNumber(productDTO.getContactNumber());
        
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            existingProduct.setCategory(category);
        }
        
        // Update tags
        if (productDTO.getTags() != null) {
            existingProduct.getTags().clear();
            
            for (String tagName : productDTO.getTags()) {
                // Try to find an existing tag
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            // Create and save a new tag if it doesn't exist
                            Tag newTag = new Tag(tagName);
                            return tagRepository.save(newTag);
                        });
                existingProduct.getTags().add(tag);
            }
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    private String saveImage(MultipartFile file, Long productId) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String fileName = productId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        
        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setCategoryName(product.getCategory().getName());
        }
        
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<String> imageUrls = product.getImages().stream()
                    .map(ProductImage::getImageUrl)
                    .collect(Collectors.toList());
            productDTO.setImageUrls(imageUrls);
            
            product.getImages().stream()
                    .filter(ProductImage::isPrimary)
                    .findFirst()
                    .ifPresent(primaryImage -> productDTO.setPrimaryImageUrl(primaryImage.getImageUrl()));
        }
        
        if (product.getTags() != null && !product.getTags().isEmpty()) {
            Set<String> tagNames = product.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet());
            productDTO.setTags(tagNames);
        }
        
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCondition(productDTO.getCondition());
        product.setBrand(productDTO.getBrand());
        product.setSuitableModels(productDTO.getSuitableModels());
        product.setYear(productDTO.getYear());
        product.setCapacity(productDTO.getCapacity());
        product.setContactNumber(productDTO.getContactNumber());
        // Category will be set separately in service methods
        
        return product;
    }
} 