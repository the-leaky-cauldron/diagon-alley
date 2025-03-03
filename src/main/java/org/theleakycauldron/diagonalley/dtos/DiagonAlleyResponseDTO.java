package org.theleakycauldron.diagonalley.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public abstract class DiagonAlleyResponseDTO {
    private LocalDateTime createdAt;
    private int statusCode;
}
