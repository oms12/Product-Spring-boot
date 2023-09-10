package com.example.demo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ProductNotFoundException extends RuntimeException{
    private String message;
    private Throwable ex;
    public ProductNotFoundException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public ProductNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
