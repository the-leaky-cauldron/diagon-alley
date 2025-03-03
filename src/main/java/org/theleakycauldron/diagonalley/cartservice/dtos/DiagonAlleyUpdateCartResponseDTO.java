package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DiagonAlleyUpdateCartResponseDTO {

    private String cartId;
    private String productId;

}
