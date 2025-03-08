package org.theleakycauldron.diagonalley.orderservice.services;

import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyCreateOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;

public interface DiagonAlleyOrderService {
    
    DiagonAlleyCreateOrderResponseDTO createOrder(Order order);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void deleteOrder(String uuid);
    DiagonAlleyGetOrderResponseDTO getOrder(String uuid);
    DiagonAlleyGetOrderListResponseDTO getOrders(String userId);
    
}
