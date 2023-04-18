package com.commerceplatform.api.orders.enums;

public enum OrderStatusEnum {
    CREATE("CREATE"),
    SHIPPING("SHIPPING"),
    DELIVERED("DELIVERED"),
    PAID("PAID");

    private final String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

}
