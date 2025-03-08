package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class DiagonAlleyRemoveItemFromCartResponseDTO {

    private String cartId;
    private String productId;

}