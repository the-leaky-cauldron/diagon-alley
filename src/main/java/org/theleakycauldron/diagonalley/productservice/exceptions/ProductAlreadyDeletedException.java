package org.theleakycauldron.diagonalley.productservice.exceptions;

public class ProductAlreadyDeletedException extends RuntimeException{
    
    public ProductAlreadyDeletedException(String message){
        super(message);
    }
}
