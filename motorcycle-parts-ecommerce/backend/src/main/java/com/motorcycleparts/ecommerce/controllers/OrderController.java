package com.motorcycleparts.ecommerce.controllers;

import com.motorcycleparts.ecommerce.dto.OrderDTO;
import com.motorcycleparts.ecommerce.models.Order;
import com.motorcycleparts.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderDTO>> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort sort = direction.equalsIgnoreCase("asc") ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OrderDTO> orders = orderService.getOrdersByUserId(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrderDTO>> getPendingOrders() {
        List<OrderDTO> pendingOrders = orderService.getPendingOrders();
        return ResponseEntity.ok(pendingOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByNumber(@PathVariable String orderNumber) {
        OrderDTO order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestParam Long userId,
            @RequestParam Long shippingAddressId,
            @RequestParam(required = false) Long billingAddressId,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String notes) {
        
        OrderDTO newOrder = orderService.createOrder(userId, shippingAddressId, billingAddressId, paymentMethod, notes);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // Exception handler for this controller
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        // Determine appropriate status code based on the error message
        if (ex.getMessage().contains("not found")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("is empty")) {
            status = HttpStatus.BAD_REQUEST;
        }
        
        return new ResponseEntity<>(error, status);
    }
} 