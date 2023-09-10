package com.example.demo.handler;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.exception.ProductDataException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
@Component
@Slf4j
public class ProductHandler {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  ProductValidator productValidator;

    public Mono<ServerResponse> getProducts(ServerRequest serverRequest) {
        Flux<ProductDto> response = productRepository.findAll()
                .subscribeOn(Schedulers.boundedElastic())
                .map(AppUtils::entityToDto);
        return  ServerResponse.ok().body(response, ProductDto.class);
    }


    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProductDto.class)
                .doOnNext(productValidator::validate)
                .subscribeOn(Schedulers.boundedElastic())
                .map(AppUtils::dtoToEntity)
                .flatMap(productRepository::save)
                .map(AppUtils::entityToDto)
                .flatMap(ServerResponse.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)::bodyValue);
    }

    public Mono<ServerResponse> findProduct(ServerRequest serverRequest) {
        System.out.println("Hello world");
        String id = serverRequest.pathVariable("id");
         return productRepository.findById(id)
                 .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found for given product id" + id)))
                 .subscribeOn(Schedulers.boundedElastic())
                 .map(AppUtils::entityToDto)
                 .flatMap(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)::bodyValue);
    }
    public Mono<ServerResponse> getProductsInRange(ServerRequest serverRequest) {
        Long min = Long.valueOf(serverRequest.queryParam("min").orElse("0"));
        Long max = Long.valueOf(serverRequest.queryParam("max").orElse("1000000000"));
        Flux<ProductDto> response = productRepository.findByPriceBetween(min, max)
                .map(AppUtils::entityToDto);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, ProductDto.class);
    }
    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        System.out.println("DELETE");
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found for given product id" + id)))
                .flatMap(item->productRepository.deleteById(id).then(ServerResponse.noContent().build()));
    }
    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found for given product id" + id)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(product->serverRequest.bodyToMono(ProductDto.class)
                        .map(updatedProduct-> {
                            product.setName(updatedProduct.getName());
                            product.setQty(updatedProduct.getQty());
                            product.setPrice(updatedProduct.getPrice());
                            return product;
                        }))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToDto)
                .flatMap(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)::bodyValue);

    }
}
