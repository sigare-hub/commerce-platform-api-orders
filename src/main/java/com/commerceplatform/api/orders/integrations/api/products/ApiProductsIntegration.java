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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApiProductsIntegration {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public ApiProductsIntegration() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public List<ProductDto> getProductsByIds(List<Long> ids) {
        try {
            String url = "http://localhost:4001/api/product";
            Map<String, List<Long>> requestBody = new HashMap<>();
            requestBody.put("ids", ids);

            HttpEntity<Map<String, List<Long>>> request = new HttpEntity<>(requestBody, this.headers);
            ResponseEntity<List<ProductDto>> response = restTemplate
                .exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<ProductDto>>() {});
            return response.getBody();
        } catch (RestClientException e) {
            // tratamento de exceção aqui, por exemplo, logar o erro e retornar uma lista vazia
            return new ArrayList<>();
        }
    }
}