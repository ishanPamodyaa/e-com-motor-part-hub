package com.motorcycleparts.ecommerce.controllers;

import com.motorcycleparts.ecommerce.dto.CartDTO;
import com.motorcycleparts.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable Long userId) {
        CartDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDTO> addItemToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        CartDTO updatedCart = cartService.addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDTO> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {
        CartDTO updatedCart = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartDTO> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        CartDTO updatedCart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cart cleared successfully");
        return ResponseEntity.ok(response);
    }
    
    // Exception handler for this controller
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
} 