package com.commerceplatform.api.orders.integrations;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

public class AccountsIntegration {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public AccountsIntegration() {
        this.headers = getHttpHeaders();
        this.restTemplate = new RestTemplate();
    }

    private static HttpHeaders getHttpHeaders() {
        var headers = new HttpHeaders();
        var credentials = "rasmooplus:r@sm00";
//        String base64 = new String(Base64.encodeBase64(credentials.getBytes()));
//        headers.add("Authorization", "Basic "+base64);
        return headers;
    }
}
