package org.theleakycauldron.diagonalley.orderservice.dtos;

import java.util.List;

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
public class DiagonAlleyGetOrderResponseDTO {
    private String orderStatus;
    private String notes;
    private String trackingNumber;
    private Double billAmount;
    private List<DiagonAlleyGetOrderItemResponseDTO> orderItems;
    private String userId;
}
