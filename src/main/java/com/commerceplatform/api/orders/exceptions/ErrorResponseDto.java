package com.commerceplatform.api.orders.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;

    @JsonProperty("http_status")
    private HttpStatus httpStatus;

    @JsonProperty("status_code")
    private Integer statusCode;
    private Object errors;
}