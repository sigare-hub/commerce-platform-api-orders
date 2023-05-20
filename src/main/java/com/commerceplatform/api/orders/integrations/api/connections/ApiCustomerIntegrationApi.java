package com.commerceplatform.api.orders.integrations.api.connections;

import com.commerceplatform.api.orders.dtos.CustomerDto;
import com.commerceplatform.api.orders.integrations.api.endpoints.CustomerEndpoints;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiCustomerIntegrationApi {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public ApiCustomerIntegrationApi() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public CustomerDto getCustomerById(Long customerIdParam) {
        try {
            HttpEntity<?> request = new HttpEntity<>(null, this.headers);
            ResponseEntity<CustomerDto> response = restTemplate
                .exchange(
                    CustomerEndpoints.getCustomerById(customerIdParam),
                    HttpMethod.GET, request, new ParameterizedTypeReference<CustomerDto>() {});
            return response.getBody();
        } catch (RestClientException e) {
            throw new RestClientException(e.getMessage());
        }
    }
}

