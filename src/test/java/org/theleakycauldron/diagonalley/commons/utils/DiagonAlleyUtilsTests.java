package org.theleakycauldron.diagonalley.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.entities.Cart;
import org.theleakycauldron.diagonalley.cartservice.entities.CartItem;
import org.theleakycauldron.diagonalley.dtos.DiagonAlleyProductKafkaRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyGetProductsResponseDTO;
import org.theleakycauldron.diagonalley.productservice.entities.ProductJpaEntity;
import org.theleakycauldron.diagonalley.productservice.entities.documents.ProductDocument;

class DiagonAlleyUtilsTests {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void convertProductToKafkaRequestDTO_mapsSecurityPrincipalAndProductFields() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("harry", "n/a", List.of())
        );

        ProductJpaEntity product = ProductJpaEntity.builder()
                .uuid(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .name("Nimbus 2000")
                .build();

        DiagonAlleyProductKafkaRequestDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertProductToKafkaRequestDTO(product);

                String responseString = response.toString();

                assertEquals(true, responseString.contains("harry"));
                assertEquals(true, responseString.contains("11111111-1111-1111-1111-111111111111"));
                assertEquals(true, responseString.contains("Nimbus 2000"));
    }

    @Test
    void convertProductToGetProductsResponseDTOs_mapsAllProducts() {
        ProductDocument first = ProductDocument.builder()
                .productName("Phoenix Feather Wand")
                .productDescription("A wand with a phoenix feather core")
                .productPrice(5000.0)
                .discount(250.0)
                .imageUrl("https://example.com/wand.png")
                .productCategory("Wands")
                .manufacturerName("Ollivanders")
                .tags(List.of("magic", "wand"))
                .rating(4.8)
                .build();

        ProductDocument second = ProductDocument.builder()
                .productName("Cloak of Invisibility")
                .productDescription("A cloak that hides the wearer")
                .productPrice(25000.0)
                .discount(0.0)
                .imageUrl("https://example.com/cloak.png")
                .productCategory("Robes")
                .manufacturerName("Weasleys' Wizard Wheezes")
                .tags(List.of("stealth"))
                .rating(5.0)
                .build();

        DiagonAlleyGetProductsResponseDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertProductToGetProductsResponseDTOs(List.of(first, second));

        assertEquals(2, response.getDiagonAlleyGetProductResponseDTOList().size());
        assertEquals("Phoenix Feather Wand", response.getDiagonAlleyGetProductResponseDTOList().get(0).getName());
        assertEquals("Cloak of Invisibility", response.getDiagonAlleyGetProductResponseDTOList().get(1).getName());
    }

    @Test
    void convertCartToCartResponseDto_mapsRequestAndCartState() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .items(new ArrayList<>())
                .build();

        DiagonAlleyAddItemToCartRequestDTO request = new DiagonAlleyAddItemToCartRequestDTO("product-1", 2, 125.5);

        DiagonAlleyAddItemToCartResponseDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertCartToCartResponseDto("user-1", request, cart);

        assertEquals("22222222-2222-2222-2222-222222222222", response.getCartId());
        assertEquals("user-1", response.getUserId());
        assertEquals("product-1", response.getProductId());
        assertEquals(2, response.getQuantity());
        assertEquals(251.0, response.getTotalPrice());
    }

    @Test
    void convertCartToGetCartResponseDTO_mapsCartItems() {
        Cart cart = Cart.builder()
                .userId("user-1")
                .totalPrice(80.0)
                .items(List.of(
                        CartItem.builder().productId("product-1").quantity(2).unitPrice(20.0).build(),
                        CartItem.builder().productId("product-2").quantity(1).unitPrice(40.0).build()
                ))
                .build();

        DiagonAlleyGetCartResponseDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertCartToGetCartResponseDTO(cart);

        assertEquals("user-1", response.getUserId());
        assertEquals(80.0, response.getTotalPrice());
        assertEquals(2, response.getProducts().size());
        assertEquals("product-1", response.getProducts().get(0).getProductId());
        assertEquals(2, response.getProducts().get(0).getQuantity());
    }

    @Test
    void convertCartToDeleteCartResponseDTO_mapsCartIdentity() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .userId("user-1")
                .build();

        DiagonAlleyDeleteCartResponseDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertCartToDeleteCartResponseDTO(cart);

        assertEquals("33333333-3333-3333-3333-333333333333", response.getCartId());
        assertEquals("user-1", response.getUserId());
    }

    @Test
    void convertCartToUpdateCartResponseDTO_mapsCartAndProduct() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                .build();

        DiagonAlleyUpdateCartResponseDTO response = org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils.convertCartToUpdateCartResponseDTO(cart, "product-2");

        assertEquals("44444444-4444-4444-4444-444444444444", response.getCartId());
        assertEquals("product-2", response.getProductId());
    }
}
