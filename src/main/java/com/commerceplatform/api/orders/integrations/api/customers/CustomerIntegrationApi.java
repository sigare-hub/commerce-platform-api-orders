package com.commerceplatform.api.orders.integrations.api.customers;

import com.commerceplatform.api.orders.integrations.api.Endpoints;
import com.commerceplatform.api.orders.integrations.api.customers.dtos.CustomerDto;
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
public class CustomerIntegrationApi {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public CustomerIntegrationApi() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public List<CustomerDto> getProducts() {
        try {
            HttpEntity<CustomerDto> request = new HttpEntity<>(null, this.headers);
            String url = Endpoints.apiCustomerUrl();
            ResponseEntity<List<CustomerDto>> response = restTemplate
                    .exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<CustomerDto>>() {});
            return response.getBody();
        } catch (RestClientException e) {
            // tratamento de exceção aqui, por exemplo, logar o erro e retornar uma lista vazia
            return new ArrayList<>();
        }
    }
}
