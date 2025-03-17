package com.motorcycleparts.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public Cart(User user) {
        this.user = user;
    }

    public void recalculateTotal() {
        this.totalPrice = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Product product, int quantity) {
        CartItem existingItem = findItemByProduct(product);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(this);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            items.add(newItem);
        }
        
        recalculateTotal();
    }

    public void removeItem(Product product) {
        CartItem itemToRemove = findItemByProduct(product);
        if (itemToRemove != null) {
            items.remove(itemToRemove);
            recalculateTotal();
        }
    }

    public void updateItemQuantity(Product product, int quantity) {
        CartItem item = findItemByProduct(product);
        if (item != null) {
            item.setQuantity(quantity);
            recalculateTotal();
        }
    }

    private CartItem findItemByProduct(Product product) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }
} 