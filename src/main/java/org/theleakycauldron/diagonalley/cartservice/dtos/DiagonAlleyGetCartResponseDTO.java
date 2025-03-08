package org.theleakycauldron.diagonalley.cartservice.dtos;

import java.util.List;

import lombok.*;

@Builder
@Getter
@Setter
public class DiagonAlleyGetCartResponseDTO {

    private double totalPrice;
    private String userId;
    private List<DiagonAlleyCartItem> products;
    
}
