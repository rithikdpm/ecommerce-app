package com.rithik.ecommerce.service;

import com.rithik.ecommerce.model.CartItem;
import com.rithik.ecommerce.model.Product;
import com.rithik.ecommerce.repository.CartItemRepository;
import com.rithik.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public CartService(CartItemRepository cartRepo, ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    public List<CartItem> getCart() {
        return cartRepo.findAll();
    }

    public CartItem addToCart(Long productId, int quantity) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        CartItem item = cartRepo.findByProductId(productId).orElseGet(() -> {
            CartItem c = new CartItem();
            c.setProduct(product);
            c.setQuantity(0);
            return c;
        });

        int newQty = item.getQuantity() + quantity;
        if (newQty > product.getStock()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Only " + product.getStock() + " items in stock");
        }
        item.setQuantity(newQty);
        return cartRepo.save(item);
    }

    public void removeItem(Long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }

    public void clearCart() {
        cartRepo.deleteAll();
    }
}
