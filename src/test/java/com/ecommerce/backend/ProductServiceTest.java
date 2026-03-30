package com.ecommerce.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; // Repository la mock kela

    @InjectMocks
    private ProductService productService; // Service madhe mock repository inject keli

    @Test
    void testGetProductById() {
        // 1. Arrange (Data tayar kara)
        Product product = new Product();
        product.setId(1L);
        product.setName("Gaming Laptop");
        product.setPrice(new BigDecimal("75000"));

        // Jeva repository kade 1L magu, teva ha product dya
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // 2. Act (Method call kara)
        Product found = productService.getProductById(1L);

        // 3. Assert (Check kara ki result barobar aahe ka)
        assertNotNull(found);
        assertEquals("Gaming Laptop", found.getName());
        verify(productRepository, times(1)).findById(1L); // Check kara ki repo call jhali ka
    }
}