package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class DiagonAlleyAddItemToCartRequestDTO {
    
    private String productUuid;
    private int quantity;
    private double price;

}
