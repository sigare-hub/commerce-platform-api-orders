package com.commerceplatform.api.orders.integrations.api.endpoints;

public class AccountsEndpoints {
    private static final String API_CUSTOMER_URL = "http://localhost:4000/api";

    private AccountsEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUserById(Long customerId) {
        return API_CUSTOMER_URL + "/user/" + customerId;
    }
}
