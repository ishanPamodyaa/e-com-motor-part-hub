package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.dto.WishlistItemDTO;
import com.motorcycleparts.ecommerce.models.Product;
import com.motorcycleparts.ecommerce.models.User;
import com.motorcycleparts.ecommerce.models.WishlistItem;
import com.motorcycleparts.ecommerce.repositories.ProductRepository;
import com.motorcycleparts.ecommerce.repositories.UserRepository;
import com.motorcycleparts.ecommerce.repositories.WishlistItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {
    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<WishlistItemDTO> getWishlistByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Page<WishlistItem> wishlistItems = wishlistItemRepository.findByUser(user, pageable);
        return wishlistItems.map(this::convertToDTO);
    }

    @Transactional
    public WishlistItemDTO addToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (wishlistItemRepository.existsByUserAndProduct(user, product)) {
            throw new RuntimeException("Product already in wishlist");
        }
        
        WishlistItem wishlistItem = new WishlistItem(user, product);
        wishlistItem = wishlistItemRepository.save(wishlistItem);
        
        return convertToDTO(wishlistItem);
    }

    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        WishlistItem wishlistItem = wishlistItemRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        
        wishlistItemRepository.delete(wishlistItem);
    }

    public boolean isInWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return wishlistItemRepository.existsByUserAndProduct(user, product);
    }

    private WishlistItemDTO convertToDTO(WishlistItem wishlistItem) {
        WishlistItemDTO wishlistItemDTO = new WishlistItemDTO();
        wishlistItemDTO.setId(wishlistItem.getId());
        wishlistItemDTO.setUserId(wishlistItem.getUser().getId());
        wishlistItemDTO.setProductId(wishlistItem.getProduct().getId());
        wishlistItemDTO.setProductName(wishlistItem.getProduct().getName());
        wishlistItemDTO.setPrice(wishlistItem.getProduct().getPrice());
        wishlistItemDTO.setCondition(wishlistItem.getProduct().getCondition().toString());
        wishlistItemDTO.setAddedDate(wishlistItem.getAddedDate());
        
        // Get primary image if available
        if (wishlistItem.getProduct().getImages() != null && !wishlistItem.getProduct().getImages().isEmpty()) {
            wishlistItem.getProduct().getImages().stream()
                    .filter(img -> img.isPrimary())
                    .findFirst()
                    .ifPresent(img -> wishlistItemDTO.setProductImage(img.getImageUrl()));
        }
        
        return wishlistItemDTO;
    }
} 