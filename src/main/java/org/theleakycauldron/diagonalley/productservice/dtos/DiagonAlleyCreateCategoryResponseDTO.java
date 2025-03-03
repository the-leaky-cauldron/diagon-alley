package org.theleakycauldron.diagonalley.productservice.dtos;

import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
public class DiagonAlleyCreateCategoryResponseDTO extends DiagonAlleyResponseDTO {
    private String response;
}
