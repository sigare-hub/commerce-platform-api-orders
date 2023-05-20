package com.commerceplatform.api.orders.integrations.api.endpoints;

public class CustomerEndpoints {
    private static final String API_CUSTOMER_URL = "http://localhost:4000/api";

    private CustomerEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCustomerById(Long customerId) {
        return API_CUSTOMER_URL + "/product/by-ids?id=" + customerId;
    }
}
