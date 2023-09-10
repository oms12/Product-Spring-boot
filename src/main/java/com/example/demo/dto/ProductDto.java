package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductDto {
    @NotNull(message = "name can't be null")
    @NotEmpty(message = "name can't be empty")
    private  String name;
    @Min(value = 0L, message = "qty can't be negative")
    private  Long qty;
    @Min(value = 0L, message = "qty can't be negative")
    private  Long price;
}
