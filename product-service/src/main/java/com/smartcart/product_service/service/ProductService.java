package com.smartcart.product_service.service;

import com.smartcart.product_service.entity.Product;
import com.smartcart.product_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    NEW IMPORT ADDED
    ----------------
    This imports our custom exception class.
*/
import com.smartcart.product_service.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;

    // Add Product
    public Product addProduct(Product product) {
        logger.info("Adding product: {}", product.getName());
        return productRepository.save(product);

    }

    // Get All Products
    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    /*
        UPDATED METHOD
        --------------
        OLD:
            .orElse(null)

        NEW:
            .orElseThrow(...)

        WHY?
        ----
        Instead of returning null when product not found,
        we throw a custom exception.
    */
    public Product getProductById(Long id) {
        logger.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id));
    }

    /*
        UPDATED METHOD
        --------------
        OLD:
            Used if(existingProduct != null)
            and return null

        NEW:
            Directly throws exception if product missing.

        WHY?
        ----
        Cleaner backend architecture.
    */
    public Product updateProduct(Long id, Product updatedProduct) {
        logger.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct);
    }

    // Delete Product
    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }
}