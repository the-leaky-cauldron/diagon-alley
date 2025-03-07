package org.theleakycauldron.diagonalley.orderservice.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import lombok.NoArgsConstructor;
import org.theleakycauldron.diagonalley.commons.basemodels.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseModel{
    private OrderStatus orderStatus;
    private String notes;
    private String trackingNumber;
    private Double billAmount;
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "order"
    )
    private List<OrderItem> orderItems;
    private String userId;
}
