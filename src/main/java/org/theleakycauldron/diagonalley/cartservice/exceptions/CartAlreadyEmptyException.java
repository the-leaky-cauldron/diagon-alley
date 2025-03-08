package org.theleakycauldron.diagonalley.cartservice.exceptions;

public class CartAlreadyEmptyException extends RuntimeException {

	public CartAlreadyEmptyException(String message) {
		super(message);
	}

}
