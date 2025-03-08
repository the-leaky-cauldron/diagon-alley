package org.theleakycauldron.diagonalley.cartservice.exceptions;

public class NoCartFoundException extends RuntimeException{
    
    public NoCartFoundException(String message){
        super(message);
    }
}
