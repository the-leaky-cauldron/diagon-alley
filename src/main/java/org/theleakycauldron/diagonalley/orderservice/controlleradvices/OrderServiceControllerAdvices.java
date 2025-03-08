package org.theleakycauldron.diagonalley.orderservice.controlleradvices;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.theleakycauldron.diagonalley.orderservice.exceptions.CannotModifyDeletedOrderException;
import org.theleakycauldron.diagonalley.orderservice.exceptions.OrderNotFoundException;

public class OrderServiceControllerAdvices {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> orderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.status(404).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
    }

    @ExceptionHandler(CannotModifyDeletedOrderException.class)
    public ResponseEntity<ErrorResponse> cannotModifyDeletedOrder(CannotModifyDeletedOrderException ex) {
        return ResponseEntity.status(400).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(400), ex.getMessage()));
    }
}
