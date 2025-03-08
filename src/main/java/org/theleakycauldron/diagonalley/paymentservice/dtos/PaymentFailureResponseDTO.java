package org.theleakycauldron.diagonalley.paymentservice.dtos;

import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentFailureResponseDTO {
    private String message;
}
