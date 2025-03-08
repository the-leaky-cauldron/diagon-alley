package org.theleakycauldron.diagonalley.productservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.theleakycauldron.diagonalley.commons.annotations.ValidURL;
import org.theleakycauldron.diagonalley.dtos.DiagonAlleyResponseDTO;

import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiagonAlleyUpdateProductRequestDTO extends  DiagonAlleyResponseDTO{
    private String name;
    private String uuid;
    private String description;
    private String manufacturer;
    private String productCategory;
    private double price;
    private double discount;
    @ValidURL
    private String imageUrl;
    private List<String> tags;
    public double rating;
}
