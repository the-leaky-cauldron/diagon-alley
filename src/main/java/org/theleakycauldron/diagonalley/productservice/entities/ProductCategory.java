package org.theleakycauldron.diagonalley.productservice.entities;

import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(
        indexes = {
                @Index(name = "idx_category_uuid", columnList = "uuid")
        }
)
public class ProductCategory extends BaseModel{
    private String name;
}
