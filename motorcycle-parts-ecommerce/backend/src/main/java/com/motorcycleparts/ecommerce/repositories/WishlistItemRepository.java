package com.motorcycleparts.ecommerce.repositories;

import com.motorcycleparts.ecommerce.models.Product;
import com.motorcycleparts.ecommerce.models.User;
import com.motorcycleparts.ecommerce.models.WishlistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    Page<WishlistItem> findByUser(User user, Pageable pageable);
    
    Optional<WishlistItem> findByUserAndProduct(User user, Product product);
    
    boolean existsByUserAndProduct(User user, Product product);
    
    void deleteByUserAndProduct(User user, Product product);
} 