package com.github.alexbabka.stock.web.error;

/**
 * Pojo to represent api validation error message,
 * that will be returned to the consumer of REST API.
 */
public class ApiError {
    private final String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
