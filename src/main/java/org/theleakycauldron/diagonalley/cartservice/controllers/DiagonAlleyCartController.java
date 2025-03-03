package org.theleakycauldron.diagonalley.cartservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.services.DiagonAlleyCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diagonAlley/cart")
public class DiagonAlleyCartController {

    private DiagonAlleyCartService cartService;

    public DiagonAlleyCartController(
        DiagonAlleyCartService cartService
    ) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<DiagonAlleyAddItemToCartResponseDTO> addItemToCart(@RequestBody DiagonAlleyAddItemToCartRequestDTO request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        DiagonAlleyAddItemToCartResponseDTO createCartResponseDTO = cartService.addItemToCart(userId, request);

        return ResponseEntity.ok(createCartResponseDTO);

    }

    @DeleteMapping
    public ResponseEntity<DiagonAlleyRemoveItemFromCartResponseDTO> removeItemFromCart(@RequestBody DiagonAlleyRemoveItemFromCartRequestDTO request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        DiagonAlleyRemoveItemFromCartResponseDTO deleteCartResponseDTO = cartService.removeItemFromCart(userId, request.getProductId());

        return ResponseEntity.ok(deleteCartResponseDTO);

    }

    @PutMapping("/item")
    public ResponseEntity<DiagonAlleyUpdateCartResponseDTO> updateItemQuantity(@RequestParam String userId, @RequestParam String productId, @RequestParam int quantity) {

        DiagonAlleyUpdateCartResponseDTO updateCartResponseDTO = cartService.updateItemQuantity(userId, productId, quantity);

        return ResponseEntity.ok(updateCartResponseDTO);

    }

    @GetMapping
    public ResponseEntity<DiagonAlleyGetCartResponseDTO> getCart(@RequestParam String userId) {

        DiagonAlleyGetCartResponseDTO getCartResponseDTO = cartService.getCart(userId);

        return ResponseEntity.ok(getCartResponseDTO);

    }

    @DeleteMapping("/clear")
    public ResponseEntity<DiagonAlleyDeleteCartResponseDTO> clearCart(@RequestParam String userId) {

        DiagonAlleyDeleteCartResponseDTO deleteCartResponseDTO = cartService.clearCart(userId);

        return ResponseEntity.ok(deleteCartResponseDTO);

    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout(@RequestParam String userId) {

        cartService.checkout(userId);

        return ResponseEntity.ok().build();

    }
}
