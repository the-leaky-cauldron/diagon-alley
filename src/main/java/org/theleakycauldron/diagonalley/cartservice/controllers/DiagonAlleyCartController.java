package org.theleakycauldron.diagonalley.cartservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.theleakycauldron.diagonalley.cartservice.dtos.*;
import org.theleakycauldron.diagonalley.cartservice.services.DiagonAlleyCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diagon-alley/cart")
@Tag(name = "Diagon alley Cart API", description = "API for managing cart")
public class DiagonAlleyCartController {

    private final DiagonAlleyCartService cartService;

    public DiagonAlleyCartController(
        DiagonAlleyCartService cartService
    ) {
        this.cartService = cartService;
    }

    @Operation(summary = "Adds item to cart", description = "Returns details of item added")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item added to cart"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @PostMapping("/item")
    public ResponseEntity<DiagonAlleyAddItemToCartResponseDTO> addItemToCart(@RequestBody DiagonAlleyAddItemToCartRequestDTO request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        DiagonAlleyAddItemToCartResponseDTO createCartResponseDTO = cartService.addItemToCart(userId, request);

        return ResponseEntity.ok(createCartResponseDTO);

    }

    @Operation(summary = "Removes item to cart", description = "Returns details of item removed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item removed from cart"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @DeleteMapping("/item")
    public ResponseEntity<DiagonAlleyRemoveItemFromCartResponseDTO> removeItemFromCart(@RequestBody DiagonAlleyRemoveItemFromCartRequestDTO request) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        DiagonAlleyRemoveItemFromCartResponseDTO deleteCartResponseDTO = cartService.removeItemFromCart(userId, request.getProductId());

        return ResponseEntity.ok(deleteCartResponseDTO);

    }

    @Operation(summary = "Updates an item in cart", description = "Returns details of item added")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item updated into cart"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @PutMapping("/item")
    public ResponseEntity<DiagonAlleyUpdateCartResponseDTO> updateItemQuantity(@RequestParam String userId, @RequestParam String productId, @RequestParam int quantity) {

        DiagonAlleyUpdateCartResponseDTO updateCartResponseDTO = cartService.updateItemQuantity(userId, productId, quantity);

        return ResponseEntity.ok(updateCartResponseDTO);

    }

    @Operation(summary = "Gets the cart", description = "Returns cart details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cart of user"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @GetMapping
    public ResponseEntity<DiagonAlleyGetCartResponseDTO> getCart(@RequestParam String userId) {

        DiagonAlleyGetCartResponseDTO getCartResponseDTO = cartService.getCart(userId);

        return ResponseEntity.ok(getCartResponseDTO);

    }
    @Operation(summary = "Clears the cart", description = "Returns details of cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cart cleared"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @DeleteMapping("/clear")
    public ResponseEntity<DiagonAlleyDeleteCartResponseDTO> clearCart(@RequestParam String userId) {

        DiagonAlleyDeleteCartResponseDTO deleteCartResponseDTO = cartService.clearCart(userId);

        return ResponseEntity.ok(deleteCartResponseDTO);

    }
    @Operation(summary = "Checkouts the cart", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item added to cart"),
            @ApiResponse(responseCode = "500", description = "any server error")
    })
    @PostMapping("/checkout")
    public ResponseEntity<DiagonAlleyPaymentResponseDTO> checkout(@RequestParam String userId) {

        DiagonAlleyPaymentResponseDTO response = cartService.checkout(userId);

        return ResponseEntity.ok(response);

    }
}
