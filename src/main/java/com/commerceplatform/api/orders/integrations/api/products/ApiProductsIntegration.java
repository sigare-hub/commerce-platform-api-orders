package com.commerceplatform.api.orders.integrations.api.products;

import com.commerceplatform.api.orders.integrations.api.products.dtos.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiProductsIntegration {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public ApiProductsIntegration(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public List<ProductDto> getProducts() {
        try {
            HttpEntity<ProductDto> request = new HttpEntity<>(null, this.headers);
            String url = "http://localhost:4001/api/product";
            ResponseEntity<List<ProductDto>> response = restTemplate
                .exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<ProductDto>>() {});
            return response.getBody();
        } catch (RestClientException e) {
            // tratamento de exceção aqui, por exemplo, logar o erro e retornar uma lista vazia
            return new ArrayList<>();
        }
    }
}