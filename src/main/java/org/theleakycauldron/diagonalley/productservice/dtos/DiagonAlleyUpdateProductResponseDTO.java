package org.theleakycauldron.diagonalley.productservice.dtos;

import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiagonAlleyUpdateProductResponseDTO extends DiagonAlleyResponseDTO {
    private String response;
    private String uuid;
}
