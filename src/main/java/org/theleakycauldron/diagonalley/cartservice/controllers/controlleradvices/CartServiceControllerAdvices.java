package org.theleakycauldron.diagonalley.cartservice.controllers.controlleradvices;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.theleakycauldron.diagonalley.cartservice.exceptions.CartAlreadyEmptyException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.InvalidCartToAddCartItemsException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoCartFoundException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoItemFoundInCartException;

@ControllerAdvice
public class CartServiceControllerAdvices {

	@ExceptionHandler(CartAlreadyEmptyException.class)
	public ResponseEntity<ErrorResponse> cartAlreadyEmpty(CartAlreadyEmptyException ex) {
		return ResponseEntity.status(405)
				.body(ErrorResponse
						.create(ex, HttpStatusCode.valueOf(405), ex.getMessage()));
	}

	@ExceptionHandler(InvalidCartToAddCartItemsException.class)
	public ResponseEntity<ErrorResponse> invalidCartToAddCartItems(InvalidCartToAddCartItemsException ex) {
		return ResponseEntity.status(405)
				.body(ErrorResponse
						.create(ex, HttpStatusCode.valueOf(405), ex.getMessage()));
	}

	@ExceptionHandler(NoCartFoundException.class)
	public ResponseEntity<ErrorResponse> noCartFound(InvalidCartToAddCartItemsException ex) {
		return ResponseEntity.status(404)
				.body(ErrorResponse
						.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
	}

	@ExceptionHandler(NoItemFoundInCartException.class)
	public ResponseEntity<ErrorResponse> noItemFoundInCart(InvalidCartToAddCartItemsException ex) {
		return ResponseEntity.status(404)
				.body(ErrorResponse
						.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
	}
}
