package edu.icet.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {


//    final CartService cartService;
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<CartDTO> getUserCart(@PathVariable Integer userId) {
//        CartDTO cart = cartService.getCartByUserId(userId);
//        return ResponseEntity.ok(cart);
//    }
//
//    @PostMapping("/{userId}/items")
//    public ResponseEntity<CartDTO> addItemToCart(
//            @PathVariable Long userId,
//            @RequestParam Long productId,
//            @RequestParam Integer quantity) {
//        CartDTO updatedCart = cartService.addItemToCart(userId, productId, quantity);
//        return ResponseEntity.ok(updatedCart);
//    }
//
//    @PutMapping("/{userId}/items/{productId}")
//    public ResponseEntity<CartDTO> updateCartItem(
//            @PathVariable Long userId,
//            @PathVariable Long productId,
//            @RequestParam Integer quantity) {
//        CartDTO updatedCart = cartService.updateCartItemQuantity(userId, productId, quantity);
//        return ResponseEntity.ok(updatedCart);
//    }
//
//    @DeleteMapping("/{userId}/items/{productId}")
//    public ResponseEntity<CartDTO> removeCartItem(
//            @PathVariable Long userId,
//            @PathVariable Long productId) {
//        CartDTO updatedCart = cartService.removeItemFromCart(userId, productId);
//        return ResponseEntity.ok(updatedCart);
//    }
//
//    @DeleteMapping("/{userId}/clear")
//    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
//        cartService.clearCart(userId);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Cart cleared successfully");
//        return ResponseEntity.ok(response);
//    }
//
//    // Exception handler for this controller
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", ex.getMessage());
//        return ResponseEntity.badRequest().body(error);
//    }
} 