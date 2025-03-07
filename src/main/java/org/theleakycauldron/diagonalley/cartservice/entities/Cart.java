package org.theleakycauldron.diagonalley.cartservice.entities;

import java.util.List;

import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity(name = "CARTS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
    indexes = {
        @Index(name = "idx_user_id", columnList = "userId")
    }
)
@SuperBuilder
public class Cart extends BaseModel{

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true,
            mappedBy = "cart")
    private List<CartItem> items;

    private String userId;

    private double totalPrice;
    
}
