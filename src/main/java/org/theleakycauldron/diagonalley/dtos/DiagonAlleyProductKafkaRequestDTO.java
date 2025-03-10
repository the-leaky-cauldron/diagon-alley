package org.theleakycauldron.diagonalley.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiagonAlleyProductKafkaRequestDTO {
    private String userId;
    private String productId;
    private String name;
}