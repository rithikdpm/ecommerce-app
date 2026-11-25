package com.rithik.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    @Column(length = 300)
    private String address;

    private BigDecimal totalAmount;

    private String paymentMethod;   // CARD / UPI / COD

    private String paymentStatus;   // PAID / PENDING

    private String status;          // PLACED / SHIPPED / DELIVERED

    private LocalDateTime createdAt;

    // One order -> many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();
}
