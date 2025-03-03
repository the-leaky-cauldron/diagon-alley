package org.theleakycauldron.diagonalley.orderservice.services;

import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyCreateOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;

public interface DiagonAlleyOrderService {
    
    public DiagonAlleyCreateOrderResponseDTO createOrder(Order order);
    public void updateOrderStatus(String orderId, OrderStatus orderStatus);
    public void deleteOrder(String uuid);
    public DiagonAlleyGetOrderResponseDTO getOrder(String uuid);
    public DiagonAlleyGetOrderListResponseDTO getOrders(String userId);
    
}
