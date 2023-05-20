package com.commerceplatform.api.orders.integrations.api.endpoints;

import org.springframework.util.StringUtils;

import java.util.List;

public class ProductsEndpoints {
    private static final String API_PRODUCTS_URL = "http://localhost:4001/api";

    private ProductsEndpoints() {
        throw new IllegalStateException("Utility class");
    }

    public static String getProductsByIds(List<Long> ids) {
        var params = StringUtils.collectionToDelimitedString(ids, ",");
        return API_PRODUCTS_URL + "/product/by-ids?ids=" + params;
    }
}
