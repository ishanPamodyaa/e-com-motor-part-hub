package edu.icet.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
// @RequiredArgsConstructor
public class CartController {
    // final private CartService cartService;

    // @GetMapping("/{userId}")
    // public ResponseEntity<CartDTO> getUserCart(@PathVariable Integer userId) {
    //     CartDTO cart = cartService.getCartByUserId(userId);
    //     return ResponseEntity.ok(cart);
    // }

    // @PostMapping("/{userId}/items")
    // public ResponseEntity<CartDTO> addItemToCart(
    //         @PathVariable Long userId,
    //         @RequestParam Long productId,
    //         @RequestParam Integer quantity) {
    //     CartDTO updatedCart = cartService.addItemToCart(userId, productId, quantity);
    //     return ResponseEntity.ok(updatedCart);
    // }
}