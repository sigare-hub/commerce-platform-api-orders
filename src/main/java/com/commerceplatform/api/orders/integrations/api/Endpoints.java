package com.commerceplatform.api.orders.integrations.api;

public class Endpoints {
    private static final String API_CUSTOMER_URL = "http://localhost:4001";

    private Endpoints() {
        throw new IllegalStateException("Utility class");
    }

    public static String apiCustomerUrl() {
        return API_CUSTOMER_URL;
    }
}
