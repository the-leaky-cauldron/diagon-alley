package org.theleakycauldron.diagonalley.productservice.controllers.controlleradvices;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.theleakycauldron.diagonalley.productservice.exceptions.CategoryAlreadyExistsException;
import org.theleakycauldron.diagonalley.productservice.exceptions.CategoryNotFoundException;
import org.theleakycauldron.diagonalley.productservice.exceptions.ProductAlreadyExistsException;
import org.theleakycauldron.diagonalley.productservice.exceptions.ProductNotFoundException;

import java.util.Optional;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@ControllerAdvice
public class DiagonAlleyControllerAdvice {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse>  categoryNotFound(CategoryNotFoundException ex){
        return ResponseEntity.status(404).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> categoryAlreadyExists(CategoryAlreadyExistsException ex){
        return ResponseEntity.of(Optional.of(ErrorResponse.create(ex, HttpStatusCode.valueOf(409), ex.getMessage())));
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> productAlreadyExists(ProductAlreadyExistsException ex){
        return ResponseEntity.of(Optional.of(ErrorResponse.create(ex, HttpStatusCode.valueOf(409), ex.getMessage())));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse>  productNotFoundExists(ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(RuntimeException ex){
        return ResponseEntity.status(500).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(500), ex.getMessage()));
    }
}
