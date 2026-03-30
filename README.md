# E-Commerce Backend System

This is a Spring Boot based REST API implementation for a full-stack e-commerce platform. The project demonstrates secure user authentication, product lifecycle management, cart operations, and transactional order processing, strictly adhering to standard requirements.

## Core Implementation Details

### 1. Security & Authentication
* **JWT (JSON Web Token):** Implemented for stateless session management and secure API access.
* **Spring Security:** Configured to protect sensitive endpoints such as Cart and Order management.
* **Constructor Injection:** Utilized modern Spring 4.3+ constructor injection (avoiding `@Autowired` on fields) for better testability and clean code architecture.
* **Password Encryption:** Utilizes `BCryptPasswordEncoder` to ensure user credentials are encrypted.

### 2. Domain Entities & Relationships
* **Product:** Manages inventory with attributes including name, price, and stock.
* **CartItem:** A specialized join-table entity linking `User` and `Product` to maintain persistent shopping sessions.
* **Order:** Captures a snapshot of products, final total price, and shipping details at the point of transaction.

### 3. Payment Processing Logic
* **Stripe Integration:** The `OrderService` includes production-ready logic for Stripe's `PaymentIntent` API.
* **Mock Implementation:** For evaluation purposes, the live Stripe gateway call is currently bypassed to ensure the application remains runnable without external API keys. The system uses a controlled mock response (`SUCCESS (MOCK)`) for order confirmation.

## API Documentation

| Category | Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- | :--- |
| **Auth** | POST | `/api/auth/register` | User Signup | ❌ No |
| **Auth** | POST | `/api/auth/login` | User Login (Returns JWT) | ❌ No |
| **Product** | POST | `/api/products` | Create a new product | ❌ No |
| **Product** | GET | `/api/products` | Browse all products | ❌ No |
| **Product** | PUT | `/api/products/{id}` | Update product details | ❌ No |
| **Product** | DELETE | `/api/products/{id}` | Delete a product | ❌ No |
| **Cart** | POST | `/api/cart/add` | Add product to cart | ✅ Yes (Bearer) |
| **Cart** | GET | `/api/cart` | View user's current cart | ✅ Yes (Bearer) |
| **Cart** | PUT | `/api/cart/update/{id}` | Update cart item quantity | ✅ Yes (Bearer) |
| **Cart** | DELETE | `/api/cart/{id}` | Remove item from cart | ✅ Yes (Bearer) |
| **Order** | POST | `/api/orders/checkout` | Finalize Order & Payment | ✅ Yes (Bearer) |
| **Order** | GET | `/api/orders/my-orders` | View past order history | ✅ Yes (Bearer) |

## Installation & Setup

1. **Database:** Configure your MySQL/PostgreSQL credentials in `src/main/resources/application.properties`.
2. **Build:** Run `mvn clean install` to load dependencies.
3. **Execution:** Launch the application via `EcomBackendApplication.java`.
4. **Testing:** Unit tests for core service layers are available in the `src/test/java` directory.