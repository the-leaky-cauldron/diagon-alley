package org.theleakycauldron.diagonalley.orderservice.entities;

import jakarta.persistence.ManyToOne;
import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@Setter
@Getter
public class OrderItem extends BaseModel {

    private String productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    @ManyToOne
    private Order order;

}
