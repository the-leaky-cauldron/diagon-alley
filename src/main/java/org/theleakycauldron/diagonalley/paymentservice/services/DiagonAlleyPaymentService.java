package org.theleakycauldron.diagonalley.paymentservice.services;

import org.theleakycauldron.diagonalley.orderservice.entities.Order;

public interface DiagonAlleyPaymentService {

    public String getPaymentLink(Order order);
    
}
