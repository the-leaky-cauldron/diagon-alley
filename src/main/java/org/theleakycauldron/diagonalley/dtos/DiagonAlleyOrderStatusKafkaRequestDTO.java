package org.theleakycauldron.diagonalley.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class DiagonAlleyOrderStatusKafkaRequestDTO {

    private String userId;
    private String orderStatus;
    
}
