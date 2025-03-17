package com.motorcycleparts.ecommerce.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import com.motorcycleparts.ecommerce.dto.TrackingInfoDto;
import com.motorcycleparts.ecommerce.models.Order;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);

            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendWelcomeEmail(String to, String username) {
        Map<String, Object> variables = Map.of(
                "username", username,
                "loginUrl", "http://localhost:4200/login"
        );
        sendEmail(to, "Welcome to Motorcycle Parts E-commerce", "welcome-email", variables);
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        Map<String, Object> variables = Map.of(
                "resetToken", resetToken,
                "resetUrl", "http://localhost:4200/reset-password?token=" + resetToken
        );
        sendEmail(to, "Password Reset Request", "password-reset-email", variables);
    }

    public void sendOrderConfirmationEmail(String to, String orderNumber, Map<String, Object> orderDetails) {
        Map<String, Object> variables = Map.of(
                "orderNumber", orderNumber,
                "orderDetails", orderDetails
        );
        sendEmail(to, "Order Confirmation - " + orderNumber, "order-confirmation-email", variables);
    }
    
    public void sendOrderNotification(Order order) {
        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("orderNumber", order.getOrderNumber());
        orderDetails.put("totalAmount", order.getTotal());
        orderDetails.put("items", order.getItems());
        orderDetails.put("shippingAddress", order.getShippingAddress());
        orderDetails.put("paymentMethod", order.getPaymentMethod());
        orderDetails.put("paymentStatus", order.getStatus().toString());
        
        sendOrderConfirmationEmail(order.getUser().getEmail(), order.getOrderNumber(), orderDetails);
    }
    
    public void sendOrderStatusUpdate(Order order) {
        TrackingInfoDto trackingInfo = TrackingInfoDto.fromOrderNotes(order.getNotes());
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderNumber", order.getOrderNumber());
        variables.put("status", order.getStatus());
        variables.put("statusDate", java.time.LocalDateTime.now());
        
        if (trackingInfo != null) {
            variables.put("trackingInfo", trackingInfo);
        }
        
        sendEmail(
            order.getUser().getEmail(),
            "Order Status Update - " + order.getOrderNumber(),
            "order-status-update-email",
            variables
        );
    }
}