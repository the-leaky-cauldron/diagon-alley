package org.theleakycauldron.diagonalley.orderservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiagonAlleyCreateOrderResponseDTO {
    
    private String orderId;

}
