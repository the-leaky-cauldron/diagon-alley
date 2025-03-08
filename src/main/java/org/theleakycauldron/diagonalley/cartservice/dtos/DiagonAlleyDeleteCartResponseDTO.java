package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagonAlleyDeleteCartResponseDTO {
    
    private String userId;
    private String cartId;

}
