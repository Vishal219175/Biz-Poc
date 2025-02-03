package com.example.mvc_mongodb.repository;

import com.example.mvc_mongodb.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
