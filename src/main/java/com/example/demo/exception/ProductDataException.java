package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ProductDataException extends RuntimeException{
    private String message;
    public ProductDataException(String s) {
        super(s);
        this.message = s;
    }

}
