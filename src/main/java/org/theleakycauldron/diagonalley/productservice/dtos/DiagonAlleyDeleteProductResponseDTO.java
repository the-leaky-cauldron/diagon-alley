package org.theleakycauldron.diagonalley.productservice.dtos;

import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class DiagonAlleyDeleteProductResponseDTO extends DiagonAlleyResponseDTO{
    @NotNull
    private String message;
}