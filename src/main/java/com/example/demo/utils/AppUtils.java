package com.example.demo.utils;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    // static as we don't need to make an object to call these methods.
    public static ProductDto entityToDto (Product product) {
        ProductDto productDto = new ProductDto();
        // copy properties from source to target
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
