package edu.icet.controller;

import edu.icet.dto.OrderDTO;
import edu.icet.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(OrderDTO orderDTO ){

//        OrderDTO createdOrderDTO = orderService.createOrder(orderDTO);


        return null;
    }
} 