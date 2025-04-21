package edu.icet.service.impl;

import edu.icet.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

//    final CartRepository cartRepository;
//    final UserRepository userRepository;
//    final ProductRepository productRepository;
//    final ModelMapper modelMapper;
//
//    public CartDTO getCartByUserId(Integer userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseGet(() -> {
//                    Cart newCart = new Cart(user);
//                    return cartRepository.save(newCart);
//                });
//
//        return convertToDTO(cart);
//    }
//
//    @Transactional
//    public CartDTO addItemToCart(Integer userId, Integer productId, Integer quantity) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseGet(() -> {
//                    Cart newCart = new Cart(user);
//                    return cartRepository.save(newCart);
//                });
//
//        cart.addItem(product, quantity);
//        cart = cartRepository.save(cart);
//
//        return convertToDTO(cart);
//    }
//
//    @Transactional
//    public CartDTO updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        cart.updateItemQuantity(product, quantity);
//        cart = cartRepository.save(cart);
//
//        return convertToDTO(cart);
//    }
//
//    @Transactional
//    public CartDTO removeItemFromCart(Long userId, Long productId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        cart.removeItem(product);
//        cart = cartRepository.save(cart);
//
//        return convertToDTO(cart);
//    }
//
//    @Transactional
//    public void clearCart(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Cart not found"));
//
//        cart.clear();
//        cartRepository.save(cart);
//    }
//
//    private CartDTO convertToDTO(Cart cart) {
//        CartDTO cartDTO = new CartDTO();
//        cartDTO.setId(cart.getId());
//        cartDTO.setUserId(cart.getUser().getId());
//        cartDTO.setTotalPrice(cart.getTotalPrice());
//
//        List<CartDTO.CartItemDTO> itemDTOs = new ArrayList<>();
//        for (CartItem item : cart.getItems()) {
//            CartDTO.CartItemDTO itemDTO = new CartDTO.CartItemDTO();
//            itemDTO.setId(item.getId());
//            itemDTO.setProductId(item.getProduct().getId());
//            itemDTO.setProductName(item.getProduct().getName());
//
//            // Get primary image if available
//            if (item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
//                item.getProduct().getImages().stream()
//                        .filter(img -> img.isPrimary())
//                        .findFirst()
//                        .ifPresent(img -> itemDTO.setProductImage(img.getImageUrl()));
//            }
//
//            itemDTO.setPrice(item.getProduct().getPrice());
//            itemDTO.setQuantity(item.getQuantity());
//            itemDTO.setSubtotal(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
//
//            itemDTOs.add(itemDTO);
//        }
//
//        cartDTO.setItems(itemDTOs);
//        return cartDTO;
//    }
}