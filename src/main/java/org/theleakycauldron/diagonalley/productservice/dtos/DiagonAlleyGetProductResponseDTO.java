package org.theleakycauldron.diagonalley.productservice.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiagonAlleyGetProductResponseDTO {

    private String name;
    private String description;
    private double amount;
    private double discount;
    private String imageURL;
    private String productCategory;
    private String manufacturer;
    private List<String> tags;
    private double rating;

}
