# 🛒 ShopEasy - Basic E-Commerce Application

Codec Technologies Virtual Internship Task 

A simple full-stack e-commerce application built with **Spring Boot, Spring Data JPA (Hibernate), MySQL and REST APIs**, with a lightweight HTML/JavaScript frontend.

## ✨ Features
- Product catalog with search (by name / category)
- Add to cart, remove from cart, stock validation
- Checkout with **payment simulation** (UPI / Card = PAID, COD = PENDING)
- Order history with order items
- Admin section: add / delete products (full CRUD REST API)
- Sample data auto-loaded on first run
- Global exception handling + input validation

## 🧰 Tech Stack
| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3, Spring Web (REST) |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript (fetch API) |
| Build | Maven |

## 🗄️ Database Design
- `products` (id, name, description, price, stock, category, image)
- `cart_items` (id, product_id → products, quantity)
- `orders` (id, customer_name, address, total_amount, payment_method, payment_status, status, created_at)
- `order_items` (id, order_id → orders, product_id → products, quantity, price)

Relationships: **Order 1—N OrderItem**, **Product 1—N CartItem**, **Product 1—N OrderItem** (Hibernate `@OneToMany` / `@ManyToOne`).

## 🚀 How to Run
1. Install **Java 17+**, **Maven**, **MySQL**.
2. Edit `src/main/resources/application.properties` and set your MySQL username/password.
   (Database `ecommerce_db` is created automatically.)
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. Open **http://localhost:8080**

## 🔌 REST API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/products?q=` | List / search products |
| GET | `/api/products/{id}` | Get one product |
| POST | `/api/products` | Create product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/cart` | View cart |
| POST | `/api/cart` | Add item `{productId, quantity}` |
| DELETE | `/api/cart/{id}` | Remove cart item |
| POST | `/api/orders/checkout` | Place order `{customerName, address, paymentMethod}` |
| GET | `/api/orders` | List orders |
| GET | `/api/orders/{id}` | Order details |

## 📁 Project Structure
```
src/main/java/com/rithik/ecommerce
├── controller   (REST controllers + exception handler)
├── service      (business logic: cart, checkout)
├── repository   (Spring Data JPA repositories)
├── model        (JPA entities)
├── dto          (request objects)
└── DataSeeder.java
src/main/resources
├── static/index.html   (frontend)
└── application.properties
```

## 🔮 Future Improvements
- User login / registration with Spring Security + JWT
- Per-user carts and order history
- React frontend, product images upload
- Real payment gateway (Razorpay)

## 👤 Author
Rithik K — [GitHub](https://github.com/rithikdpm) | [LinkedIn](https://linkedin.com/in/rithikdpm)
