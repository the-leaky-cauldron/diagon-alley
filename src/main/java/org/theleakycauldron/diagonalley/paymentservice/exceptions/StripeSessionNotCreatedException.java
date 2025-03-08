package org.theleakycauldron.diagonalley.paymentservice.exceptions;

public class StripeSessionNotCreatedException extends RuntimeException{

    public StripeSessionNotCreatedException(String message){
        super(message);
    }

    public StripeSessionNotCreatedException(String message, Throwable e){
        super(message, e);
    }
}
