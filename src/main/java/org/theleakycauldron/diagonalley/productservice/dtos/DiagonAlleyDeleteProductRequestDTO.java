package org.theleakycauldron.diagonalley.productservice.dtos;


import java.util.UUID;

import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class DiagonAlleyDeleteProductRequestDTO extends DiagonAlleyResponseDTO{
    @NotNull
    private UUID uuid;
}