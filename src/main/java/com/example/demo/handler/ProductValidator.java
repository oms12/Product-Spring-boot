package com.example.demo.handler;

import com.example.demo.dto.ProductDto;
import com.example.demo.exception.ProductDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@Data
@NoArgsConstructor
public class ProductValidator {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator productValidator = factory.getValidator();

    public void validate(ProductDto productDto) {
        Set<ConstraintViolation<ProductDto>> constraintViolations = productValidator.validate(productDto);
        log.info("constraintViolation : {}", constraintViolations);
        if(!constraintViolations.isEmpty()) {
            String errorMessage = constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .sorted()
                    .collect(Collectors.joining(","));
            throw new ProductDataException(errorMessage);
        }
    }
}
