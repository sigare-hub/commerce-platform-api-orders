package com.commerceplatform.api.orders.exceptions;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, List<String>> errors;

    public ValidationException(Map<String, List<String>> errors) {
        super("Validation Exception");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}