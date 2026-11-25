package com.rithik.ecommerce;

import com.rithik.ecommerce.model.Product;
import com.rithik.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Inserts sample products on first run so the store is not empty. */
@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository repo;

    public DataSeeder(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) return;
        add("Wireless Headphones", "Bluetooth over-ear headphones with 20h battery", "1999", 25, "Electronics", "🎧");
        add("Smart Watch", "Fitness tracker with heart-rate monitor", "2999", 15, "Electronics", "⌚");
        add("Java Programming Book", "Complete reference for Java beginners", "499", 40, "Books", "📘");
        add("Running Shoes", "Lightweight shoes for daily running", "1499", 30, "Fashion", "👟");
        add("Backpack", "Water-resistant 30L laptop backpack", "899", 50, "Fashion", "🎒");
        add("Coffee Mug", "Ceramic mug, 350 ml", "199", 100, "Home", "☕");
        add("Mechanical Keyboard", "RGB backlit, blue switches", "2499", 12, "Electronics", "⌨️");
        add("Desk Lamp", "LED lamp with adjustable brightness", "699", 35, "Home", "💡");
    }

    private void add(String name, String desc, String price, int stock, String cat, String img) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(new BigDecimal(price));
        p.setStock(stock);
        p.setCategory(cat);
        p.setImage(img);
        repo.save(p);
    }
}
