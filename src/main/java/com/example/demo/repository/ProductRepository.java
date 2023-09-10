package com.example.demo.repository;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByPriceBetween(Long min, Long max);
}
