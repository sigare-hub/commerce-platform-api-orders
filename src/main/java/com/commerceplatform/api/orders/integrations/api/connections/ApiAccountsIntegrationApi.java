package com.commerceplatform.api.orders.integrations.api.connections;

import com.commerceplatform.api.orders.dtos.CustomerDto;
import com.commerceplatform.api.orders.integrations.api.endpoints.AccountsEndpoints;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiAccountsIntegrationApi {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public ApiAccountsIntegrationApi() {
        this.restTemplate = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    public CustomerDto getCustomerById(Long customerIdParam) {
        try {
            HttpEntity<?> request = new HttpEntity<>(null, this.headers);
            ResponseEntity<CustomerDto> response = restTemplate
                .exchange(
                    AccountsEndpoints.getUserById(customerIdParam),
                    HttpMethod.GET, request, CustomerDto.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RestClientException(e.getMessage());
        }
    }
}

