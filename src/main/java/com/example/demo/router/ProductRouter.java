package com.example.demo.router;

import com.example.demo.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class ProductRouter {
    @Bean
    RouterFunction<ServerResponse> routerFunctionProductAPIRouter(ProductHandler productHandler){
        return RouterFunctions.route(RequestPredicates.GET("/products"), productHandler::getProducts)
                .andRoute(RequestPredicates.GET("/products/find/{id}"), productHandler::findProduct)
                .andRoute(RequestPredicates.POST("/products/save"), productHandler::saveProduct)
                .andRoute(RequestPredicates.DELETE("/products/delete/{id}"), productHandler::deleteProduct)
                .andRoute(RequestPredicates.PUT("/products/update/{id}"), productHandler::updateProduct)
                .andRoute(RequestPredicates.GET("/products/find-in-range"), productHandler::getProductsInRange);
    }
}
