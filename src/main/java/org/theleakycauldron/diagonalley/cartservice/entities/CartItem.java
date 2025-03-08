package org.theleakycauldron.diagonalley.cartservice.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CartItem extends BaseModel {
    
    private String productId;
    private int quantity;
    private Double unitPrice;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Cart cart;

}
