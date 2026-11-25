package com.rithik.ecommerce.service;

import com.rithik.ecommerce.dto.CheckoutRequest;
import com.rithik.ecommerce.model.*;
import com.rithik.ecommerce.repository.CartItemRepository;
import com.rithik.ecommerce.repository.OrderRepository;
import com.rithik.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, CartItemRepository cartRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    /**
     * Checkout flow: cart -> order -> payment simulation -> reduce stock -> clear cart
     */
    @Transactional
    public Order checkout(CheckoutRequest req) {
        List<CartItem> cartItems = cartRepo.findAll();
        if (cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        Order order = new Order();
        order.setCustomerName(req.getCustomerName());
        order.setAddress(req.getAddress());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("PLACED");

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem ci : cartItems) {
            Product p = ci.getProduct();
            if (ci.getQuantity() > p.getStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Not enough stock for " + p.getName());
            }
            // reduce stock
            p.setStock(p.getStock() - ci.getQuantity());
            productRepo.save(p);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(p);
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(p.getPrice());
            order.getItems().add(oi);

            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        order.setTotalAmount(total);

        // ---- Payment simulation ----
        // COD -> payment pending until delivery, CARD/UPI -> instantly marked PAID
        if ("COD".equalsIgnoreCase(req.getPaymentMethod())) {
            order.setPaymentStatus("PENDING");
        } else {
            order.setPaymentStatus("PAID");
        }

        Order saved = orderRepo.save(order);
        cartRepo.deleteAll();
        return saved;
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAllByOrderByCreatedAtDesc();
    }

    public Order getOrder(Long id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
