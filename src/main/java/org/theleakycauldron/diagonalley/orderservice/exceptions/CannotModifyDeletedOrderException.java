package org.theleakycauldron.diagonalley.orderservice.exceptions;

public class CannotModifyDeletedOrderException extends RuntimeException {
    
    public CannotModifyDeletedOrderException(String message) {
        super(message);
    }
}
