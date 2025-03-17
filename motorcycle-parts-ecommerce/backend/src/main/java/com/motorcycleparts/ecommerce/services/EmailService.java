package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.models.Order;
import com.motorcycleparts.ecommerce.models.OrderItem;
import com.motorcycleparts.ecommerce.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${admin.email:admin@motorcycleparts.com}")
    private String adminEmail;

    public void sendOrderNotification(Order order) {
        try {
            // Send notification to admin
            MimeMessage adminMessage = mailSender.createMimeMessage();
            MimeMessageHelper adminHelper = new MimeMessageHelper(adminMessage, true);
            adminHelper.setFrom(fromEmail);
            adminHelper.setTo(adminEmail);
            adminHelper.setSubject("New Order Placed: #" + order.getOrderNumber());
            
            Context adminContext = new Context();
            adminContext.setVariable("order", order);
            adminContext.setVariable("items", order.getItems());
            adminContext.setVariable("customer", order.getUser());
            
            String adminContent = templateEngine.process("admin-order-notification", adminContext);
            adminHelper.setText(adminContent, true);
            
            mailSender.send(adminMessage);
            
            // Send confirmation to customer
            MimeMessage customerMessage = mailSender.createMimeMessage();
            MimeMessageHelper customerHelper = new MimeMessageHelper(customerMessage, true);
            customerHelper.setFrom(fromEmail);
            customerHelper.setTo(order.getUser().getEmail());
            customerHelper.setSubject("Order Confirmation: #" + order.getOrderNumber());
            
            Context customerContext = new Context();
            customerContext.setVariable("order", order);
            customerContext.setVariable("items", order.getItems());
            customerContext.setVariable("customer", order.getUser());
            
            String customerContent = templateEngine.process("customer-order-confirmation", customerContext);
            customerHelper.setText(customerContent, true);
            
            mailSender.send(customerMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email notification", e);
        }
    }

    public void sendOrderStatusUpdate(Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(order.getUser().getEmail());
            helper.setSubject("Order Status Update: #" + order.getOrderNumber());
            
            Context context = new Context();
            context.setVariable("order", order);
            context.setVariable("status", order.getStatus().toString());
            context.setVariable("customer", order.getUser());
            
            String content = templateEngine.process("order-status-update", context);
            helper.setText(content, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email notification", e);
        }
    }

    public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Motorcycle Parts E-commerce");
            
            Context context = new Context();
            context.setVariable("user", user);
            
            String content = templateEngine.process("welcome-email", context);
            helper.setText(content, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

    public void sendPasswordResetEmail(User user, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reset Request");
            
            Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("resetToken", resetToken);
            
            String content = templateEngine.process("password-reset", context);
            helper.setText(content, true);
            
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
} 