package org.theleakycauldron.diagonalley.paymentservice.controllers.controlleradvices;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.theleakycauldron.diagonalley.paymentservice.exceptions.StripeSessionNotCreatedException;

@ControllerAdvice
public class PaymentControllerAdvices {
    
    @ExceptionHandler(StripeSessionNotCreatedException.class)
    public ResponseEntity<ErrorResponse>  stripeSessionNotCreated(StripeSessionNotCreatedException ex){
        return ResponseEntity.status(500).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(500), ex.getMessage()));
    }
}
