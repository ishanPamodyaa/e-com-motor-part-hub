package com.motorcycleparts.ecommerce.repositories;

import com.motorcycleparts.ecommerce.models.Category;
import com.motorcycleparts.ecommerce.models.Product;
import com.motorcycleparts.ecommerce.models.Product.ProductCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    
    Page<Product> findByCondition(ProductCondition condition, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.brand LIKE %:keyword% OR p.suitableModels LIKE %:keyword%")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
    
    Page<Product> findByCategoryAndCondition(Category category, ProductCondition condition, Pageable pageable);
    
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id != :productId ORDER BY FUNCTION('RAND') LIMIT 4")
    List<Product> findSimilarProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0 ORDER BY p.id DESC")
    Page<Product> findInStockProducts(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.condition = :condition AND p.stockQuantity > 0 ORDER BY p.id DESC")
    Page<Product> findInStockProductsByCondition(@Param("condition") ProductCondition condition, Pageable pageable);
} 