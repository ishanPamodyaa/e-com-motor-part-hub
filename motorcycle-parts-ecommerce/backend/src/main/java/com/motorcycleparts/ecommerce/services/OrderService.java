package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.dto.OrderDTO;
import com.motorcycleparts.ecommerce.models.*;
import com.motorcycleparts.ecommerce.repositories.AddressRepository;
import com.motorcycleparts.ecommerce.repositories.CartRepository;
import com.motorcycleparts.ecommerce.repositories.OrderRepository;
import com.motorcycleparts.ecommerce.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    public Page<OrderDTO> getOrdersByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Page<Order> orders = orderRepository.findByUser(user, pageable);
        return orders.map(this::convertToDTO);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    public List<OrderDTO> getPendingOrders() {
        List<Order> pendingOrders = orderRepository.findPendingOrders();
        return pendingOrders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO createOrder(Long userId, Long shippingAddressId, Long billingAddressId, String paymentMethod, String notes) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        Address shippingAddress = addressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        
        Address billingAddress = (billingAddressId != null) 
                ? addressRepository.findById(billingAddressId)
                        .orElseThrow(() -> new RuntimeException("Billing address not found"))
                : shippingAddress;
        
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setNotes(notes);
        order.setStatus(Order.OrderStatus.PENDING);
        
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        
        order.setItems(orderItems);
        order.calculateTotals();
        
        Order savedOrder = orderRepository.save(order);
        
        // Clear the cart after creating the order
        cart.clear();
        cartRepository.save(cart);
        
        // Send notification to admin
        emailService.sendOrderNotification(savedOrder);
        
        return convertToDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        order = orderRepository.save(order);
        
        // Send notification to customer
        emailService.sendOrderStatusUpdate(order);
        
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setUserId(order.getUser().getId());
        orderDTO.setUserEmail(order.getUser().getEmail());
        orderDTO.setSubtotal(order.getSubtotal());
        orderDTO.setShippingCost(order.getShippingCost());
        orderDTO.setTax(order.getTax());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setPaymentMethod(order.getPaymentMethod());
        orderDTO.setPaymentId(order.getPaymentId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setLastUpdated(order.getLastUpdated());
        orderDTO.setNotes(order.getNotes());
        
        // Convert shipping address
        OrderDTO.AddressDTO shippingAddressDTO = new OrderDTO.AddressDTO();
        shippingAddressDTO.setId(order.getShippingAddress().getId());
        shippingAddressDTO.setStreetAddress(order.getShippingAddress().getStreetAddress());
        shippingAddressDTO.setCity(order.getShippingAddress().getCity());
        shippingAddressDTO.setState(order.getShippingAddress().getState());
        shippingAddressDTO.setPostalCode(order.getShippingAddress().getPostalCode());
        shippingAddressDTO.setCountry(order.getShippingAddress().getCountry());
        orderDTO.setShippingAddress(shippingAddressDTO);
        
        // Convert billing address
        OrderDTO.AddressDTO billingAddressDTO = new OrderDTO.AddressDTO();
        billingAddressDTO.setId(order.getBillingAddress().getId());
        billingAddressDTO.setStreetAddress(order.getBillingAddress().getStreetAddress());
        billingAddressDTO.setCity(order.getBillingAddress().getCity());
        billingAddressDTO.setState(order.getBillingAddress().getState());
        billingAddressDTO.setPostalCode(order.getBillingAddress().getPostalCode());
        billingAddressDTO.setCountry(order.getBillingAddress().getCountry());
        orderDTO.setBillingAddress(billingAddressDTO);
        
        // Convert order items
        List<OrderDTO.OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderDTO.OrderItemDTO itemDTO = new OrderDTO.OrderItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProductName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setSubtotal(item.getSubtotal());
            
            // Get primary image if available
            if (item.getProduct().getImages() != null && !item.getProduct().getImages().isEmpty()) {
                item.getProduct().getImages().stream()
                        .filter(img -> img.isPrimary())
                        .findFirst()
                        .ifPresent(img -> itemDTO.setProductImage(img.getImageUrl()));
            }
            
            orderItemDTOs.add(itemDTO);
        }
        
        orderDTO.setItems(orderItemDTOs);
        
        return orderDTO;
    }
} 