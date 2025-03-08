package org.theleakycauldron.diagonalley.outboxservice.entities;

import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;
import org.theleakycauldron.diagonalley.productservice.entities.ProductJpaEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
public class Outbox extends BaseModel {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductJpaEntity productJpaEntity;
    private boolean isPersisted;
}
