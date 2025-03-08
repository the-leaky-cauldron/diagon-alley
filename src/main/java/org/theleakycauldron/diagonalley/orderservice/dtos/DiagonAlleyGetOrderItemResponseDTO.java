package org.theleakycauldron.diagonalley.orderservice.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiagonAlleyGetOrderItemResponseDTO {
    private String productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
}
