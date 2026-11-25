package com.rithik.ecommerce.controller;

import com.rithik.ecommerce.dto.AddToCartRequest;
import com.rithik.ecommerce.model.CartItem;
import com.rithik.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartItem> getCart() {
        return service.getCart();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItem add(@Valid @RequestBody AddToCartRequest req) {
        return service.addToCart(req.getProductId(), req.getQuantity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.removeItem(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear() {
        service.clearCart();
    }
}
