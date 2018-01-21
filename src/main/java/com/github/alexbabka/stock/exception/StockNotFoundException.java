package com.github.alexbabka.stock.exception;

/**
 * Exception class for the case when we tried to find stock by id,
 * but it does not exist.
 */
public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(long stockId) {
        super("Stock with id: " + stockId + " is not found");
    }
}
