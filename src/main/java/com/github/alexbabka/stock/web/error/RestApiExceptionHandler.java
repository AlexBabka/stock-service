package com.github.alexbabka.stock.web.error;

import com.github.alexbabka.stock.exception.StockNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String field = ex.getBindingResult().getFieldError().getField();
        String code = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest().body(new ApiError(field + " is not valid. Error: " + code));
    }

    @ExceptionHandler(value = {StockNotFoundException.class})
    protected ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
