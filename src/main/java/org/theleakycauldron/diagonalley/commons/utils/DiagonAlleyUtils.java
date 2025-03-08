package org.theleakycauldron.diagonalley.commons.utils;

import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyCartItem;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.entities.Cart;
import org.theleakycauldron.diagonalley.dtos.DiagonAlleyKafkaRequestDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyGetProductResponseDTO;
import org.theleakycauldron.diagonalley.productservice.dtos.DiagonAlleyGetProductsResponseDTO;
import org.theleakycauldron.diagonalley.productservice.entities.ProductJpaEntity;
import org.theleakycauldron.diagonalley.productservice.entities.documents.ProductDocument;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

public class DiagonAlleyUtils {
    public static DiagonAlleyKafkaRequestDTO convertProductToKafkaRequestDTO(ProductJpaEntity productJpaEntity){
        return DiagonAlleyKafkaRequestDTO.builder()
                .name(productJpaEntity.getName())
                .description(productJpaEntity.getDescription())
                .amount(productJpaEntity.getPrice().getAmount())
                .discount(productJpaEntity.getPrice().getDiscount())
                .imageURL(productJpaEntity.getImageURL())
                .productCategory(productJpaEntity.getProductCategory().getName())
                .manufacturer(productJpaEntity.getManufacturer().getName())
                .tags(productJpaEntity.getTags())
                .rating(productJpaEntity.getRating())
                .build();
    }

    public static DiagonAlleyGetProductsResponseDTO convertProductToGetProductsResponseDTOs(List<ProductDocument> productDocuments) {
        List<DiagonAlleyGetProductResponseDTO> dtos = productDocuments.stream().map(
                productDocument -> DiagonAlleyGetProductResponseDTO.builder()
                        .name(productDocument.getProductName())
                        .description(productDocument.getProductDescription())
                        .amount(productDocument.getProductPrice())
                        .discount(productDocument.getDiscount())
                        .imageURL(productDocument.getImageUrl())
                        .productCategory(productDocument.getProductCategory())
                        .manufacturer(productDocument.getManufacturerName())
                        .tags(productDocument.getTags())
                        .rating(productDocument.getRating())
                        .build()
        ).collect(Collectors.toList());

        return DiagonAlleyGetProductsResponseDTO.builder()
                .diagonAlleyGetProductResponseDTOList(dtos)
                .build();
    }

    public static DiagonAlleyAddItemToCartResponseDTO convertCartToCartResponseDto(String userId, DiagonAlleyAddItemToCartRequestDTO request, Cart cart) {
        return DiagonAlleyAddItemToCartResponseDTO.builder()
                            .userId(userId)
                            .cartId(cart.getUuid().toString())
                            .quantity(request.getQuantity())
                            .productId(request.getProductUuid())
                            .totalPrice(request.getQuantity() * request.getPrice())
                            .build();

    }

    public static DiagonAlleyRemoveItemFromCartResponseDTO convertProductToCartResponseDTO(String cartId, String productId) {
        return DiagonAlleyRemoveItemFromCartResponseDTO.builder()
                        .cartId(productId)
                        .productId(productId)
                        .build();
    }

    public static DiagonAlleyGetCartResponseDTO convertCartToGetCartResponseDTO(Cart cart) {
        return DiagonAlleyGetCartResponseDTO.builder()
                    .userId(cart.getUserId())
                    .totalPrice(cart.getTotalPrice())
                    .products(
                        cart.getItems().stream().map(product -> DiagonAlleyCartItem.builder()
                                                                    .productId(product.getProductId())
                                                                    .quantity(product.getQuantity())
                                                                    .build()
                        ).toList()
                    ).build();
    }

    public static DiagonAlleyDeleteCartResponseDTO convertCartToDeleteCartResponseDTO(Cart cart) {
        
        return DiagonAlleyDeleteCartResponseDTO.builder()
                                    .cartId(cart.getUuid().toString())
                                    .userId(cart.getUserId())
                                    .build();

    }

    public static DiagonAlleyUpdateCartResponseDTO convertCartToUpdateCartResponseDTO(Cart cart, String productId) {
        return DiagonAlleyUpdateCartResponseDTO.builder()
                                                .cartId(cart.getUuid().toString())
                                                .productId(productId)
                                                .build();
    }
}
