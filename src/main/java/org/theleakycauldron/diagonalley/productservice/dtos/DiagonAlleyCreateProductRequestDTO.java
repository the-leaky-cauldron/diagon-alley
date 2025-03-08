package org.theleakycauldron.diagonalley.productservice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import org.theleakycauldron.diagonalley.commons.annotations.ValidURL;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DiagonAlleyCreateProductRequestDTO {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String manufacturer;
    @NotNull
    private String productCategory;
    @NotNull
    private double price;
    private double discount;

    @NotNull
    @ValidURL
    private String imageUrl;

    private List<String> tags;
    public double rating;
}
