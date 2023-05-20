package com.commerceplatform.api.orders.integrations.api.connections;

import com.commerceplatform.api.orders.dtos.ProductDto;
import com.commerceplatform.api.orders.integrations.api.endpoints.ProductsEndpoints;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
            HttpEntity<?> request = new HttpEntity<>(null, this.headers);
            ResponseEntity<List<ProductDto>> response = restTemplate
                .exchange(
                    ProductsEndpoints.getProductsByIds(ids),
                    HttpMethod.GET, request, new ParameterizedTypeReference<List<ProductDto>>() {});
            return response.getBody();
        } catch (RestClientException e) {
            throw new RestClientException(e.getMessage());
        }
    }
}