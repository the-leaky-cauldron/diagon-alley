package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DiagonAlleyAddItemToCartResponseDTO {
    
    private String cartId;
    private String userId;
    private String productId;
    private int quantity;
    private double totalPrice;

}
