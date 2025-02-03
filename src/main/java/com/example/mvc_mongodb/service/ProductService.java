package com.example.mvc_mongodb.service;

import com.example.mvc_mongodb.model.Product;
import com.example.mvc_mongodb.repository.ProductRepository;
import com.example.mvc_mongodb.solr.MongoToSolrSync;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    // Constructor injection of dependencies
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Fetch all products from the MongoDB
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    // Create a new product in MongoDB
    public Product createProduct(Product product) {
        logger.info("Saving product to MongoDB: {}", product.getId());
        Product savedProduct = productRepository.save(product);
        logger.info("Product saved successfully: {}", savedProduct.getId());
        return savedProduct;
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product) {
        logger.info("Updating product in MongoDB: {}", product);
        Product updatedProduct = productRepository.save(product);
        logger.info("Product updated successfully: {}", updatedProduct);
        return updatedProduct;
    }

}
