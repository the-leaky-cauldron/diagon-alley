package org.theleakycauldron.diagonalley.cartservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagonAlleyPaymentResponseDTO {
    
    private String paymentLink;

}
