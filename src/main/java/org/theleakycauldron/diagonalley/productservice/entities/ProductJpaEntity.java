package org.theleakycauldron.diagonalley.productservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        indexes = {
                @Index(name = "idx_product_uuid", columnList = "uuid")
        },
        name = "product"
)
public class ProductJpaEntity extends BaseModel{
    private String name;
    private String description;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Price price;
    @Column(length = 1000)
    private String imageURL;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;
    private double rating;
}
