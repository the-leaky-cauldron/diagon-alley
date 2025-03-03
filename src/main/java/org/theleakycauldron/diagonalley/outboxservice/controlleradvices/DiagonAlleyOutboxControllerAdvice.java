package org.theleakycauldron.diagonalley.outboxservice.controlleradvices;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.theleakycauldron.diagonalley.outboxservice.exceptions.OutboxNotExistsException;


@ControllerAdvice
public class DiagonAlleyOutboxControllerAdvice {
    
    @ExceptionHandler(OutboxNotExistsException.class)
    public ResponseEntity<ErrorResponse> outboxNotFound(OutboxNotExistsException ex){
        return ResponseEntity.status(404).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException ex){
        return ResponseEntity.status(500).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(500), ex.getMessage()));
    }
}
