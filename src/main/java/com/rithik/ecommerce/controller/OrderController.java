package com.rithik.ecommerce.controller;

import com.rithik.ecommerce.dto.CheckoutRequest;
import com.rithik.ecommerce.model.Order;
import com.rithik.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public Order checkout(@Valid @RequestBody CheckoutRequest req) {
        return service.checkout(req);
    }

    @GetMapping
    public List<Order> all() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order one(@PathVariable Long id) {
        return service.getOrder(id);
    }
}
