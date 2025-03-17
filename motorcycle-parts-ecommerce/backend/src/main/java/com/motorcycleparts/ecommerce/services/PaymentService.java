package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.dto.PaymentRequest;
import com.motorcycleparts.ecommerce.dto.PaymentResponse;
import com.motorcycleparts.ecommerce.models.Order;
import com.motorcycleparts.ecommerce.repositories.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {
    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount().multiply(new java.math.BigDecimal(100)).longValue())
                    .setCurrency(paymentRequest.getCurrency())
                    .setDescription(paymentRequest.getDescription())
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return new PaymentResponse(
                    paymentIntent.getId(),
                    paymentIntent.getClientSecret(),
                    paymentIntent.getStatus(),
                    "Payment intent created successfully"
            );
        } catch (StripeException e) {
            return new PaymentResponse(
                    null,
                    null,
                    "failed",
                    e.getMessage()
            );
        }
    }

    @Transactional
    public PaymentResponse confirmPayment(String paymentIntentId, Long orderId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            
            if ("succeeded".equals(paymentIntent.getStatus())) {
                Order order = orderRepository.findById(orderId)
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                
                order.setPaymentId(paymentIntentId);
                order.setStatus(Order.OrderStatus.PROCESSING);
                orderRepository.save(order);
                
                return new PaymentResponse(
                        paymentIntent.getId(),
                        paymentIntent.getClientSecret(),
                        paymentIntent.getStatus(),
                        "Payment confirmed successfully"
                );
            } else {
                return new PaymentResponse(
                        paymentIntent.getId(),
                        paymentIntent.getClientSecret(),
                        paymentIntent.getStatus(),
                        "Payment not succeeded"
                );
            }
        } catch (StripeException e) {
            return new PaymentResponse(
                    null,
                    null,
                    "failed",
                    e.getMessage()
            );
        }
    }

    public PaymentResponse getPaymentStatus(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            
            return new PaymentResponse(
                    paymentIntent.getId(),
                    paymentIntent.getClientSecret(),
                    paymentIntent.getStatus(),
                    "Payment status retrieved successfully"
            );
        } catch (StripeException e) {
            return new PaymentResponse(
                    null,
                    null,
                    "failed",
                    e.getMessage()
            );
        }
    }
} 