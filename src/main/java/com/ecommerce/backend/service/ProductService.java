package com.ecommerce.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.exception.ResourceNotFoundException;
import com.ecommerce.backend.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor Injection (Best practice for SOLID principles)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. CREATE
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 2. RETRIEVE ALL
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 3. RETRIEVE ONE (With Error Handling for Criteria 6)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    // 4. UPDATE
    public Product updateProduct(Long id, Product productDetails) {
        // First, check if the product exists
        Product existingProduct = getProductById(id);

        // Update the fields
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStockQuantity(productDetails.getStockQuantity());
        existingProduct.setImageUrl(productDetails.getImageUrl());

        // Save and return the updated product
        // (Remember, updatedAt is handled automatically by Hibernate!)
        return productRepository.save(existingProduct);
    }

    // 5. DELETE
    public void deleteProduct(Long id) {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }
}