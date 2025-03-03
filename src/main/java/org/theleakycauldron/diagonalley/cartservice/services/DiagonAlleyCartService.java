package org.theleakycauldron.diagonalley.cartservice.services;

import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyPaymentResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;

public interface DiagonAlleyCartService {

    DiagonAlleyAddItemToCartResponseDTO addItemToCart(String userId, DiagonAlleyAddItemToCartRequestDTO request);

    DiagonAlleyRemoveItemFromCartResponseDTO removeItemFromCart(String userId, String productId);

    DiagonAlleyUpdateCartResponseDTO updateItemQuantity(String userId, String productId, int quantity);

    DiagonAlleyGetCartResponseDTO getCart(String userId);

    DiagonAlleyDeleteCartResponseDTO clearCart(String userId);

    DiagonAlleyPaymentResponseDTO checkout(String userId);

}
