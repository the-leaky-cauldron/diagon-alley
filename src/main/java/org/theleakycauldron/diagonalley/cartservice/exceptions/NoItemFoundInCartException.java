package org.theleakycauldron.diagonalley.cartservice.exceptions;

public class NoItemFoundInCartException extends RuntimeException{

    public NoItemFoundInCartException(String message){
        super(message);
    }
    
}
