package com.example.mvc_mongodb.controller;

import com.example.mvc_mongodb.model.Product;
import com.example.mvc_mongodb.service.ProductService;
import com.example.mvc_mongodb.solr.MongoToSolrSync;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final MongoToSolrSync mongoToSolrSync;

    public ProductController(ProductService productService, MongoToSolrSync mongoToSolrSync) {
        this.productService = productService;
        this.mongoToSolrSync = mongoToSolrSync;
    }
    @GetMapping
    public List<Product> getAllProducts() {

        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PostMapping("/sync/{id}")  // Manual sync endpoint
    public ResponseEntity<String> syncProduct(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            mongoToSolrSync.syncToSolr(product.get());
            return ResponseEntity.ok("Product synced successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            Product existingProduct = productOpt.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());

            Product savedProduct = productService.updateProduct(existingProduct);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
