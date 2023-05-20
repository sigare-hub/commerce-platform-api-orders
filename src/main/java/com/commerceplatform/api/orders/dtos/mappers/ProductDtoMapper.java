package com.commerceplatform.api.orders.dtos.mappers;

import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.models.jpa.Product;

public class ProductDtoMapper {
    private ProductDtoMapper() {
        throw new IllegalStateException("Você não pode instanciar essa classe de utilitário");
    }

    public static Product mapper(ProductDto productDto) {
        return Product.builder()
            .id(productDto.getId())
            .description(productDto.getDescription())
            .name(productDto.getName())
            .build();
    }

}
