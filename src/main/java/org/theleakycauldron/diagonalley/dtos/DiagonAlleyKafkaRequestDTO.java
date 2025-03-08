package org.theleakycauldron.diagonalley.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiagonAlleyKafkaRequestDTO {
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