package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.*;

@Builder
@Getter
@Setter
public class DiagonAlleyCartItem {
    
    private String productId;
    private int quantity;

}
