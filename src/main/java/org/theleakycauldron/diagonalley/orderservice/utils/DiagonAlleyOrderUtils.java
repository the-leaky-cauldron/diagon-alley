package org.theleakycauldron.diagonalley.orderservice.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyCreateOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderItemResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderItem;

public class DiagonAlleyOrderUtils {
    
    public static DiagonAlleyCreateOrderResponseDTO convertOrderToOrderResponseDTO(Order order) {
        return DiagonAlleyCreateOrderResponseDTO.builder()
                .orderId(order.getUuid().toString())
                .build();
    }

    public static DiagonAlleyGetOrderResponseDTO convertOrderToGetOrderResponseDTO(Order order) {
        return DiagonAlleyGetOrderResponseDTO.builder()
                .orderStatus(order.getOrderStatus().name())
                .notes(order.getNotes())
                .orderItems(
                    order.getOrderItems().stream()
                    .map(orderItem -> convertOrderItemToGetOrderItemResponseDTO(orderItem))
                                        .collect(Collectors.toList())
                    )
                    .trackingNumber(order.getTrackingNumber())
                    .billAmount(order.getBillAmount())
                    .userId(order.getUserId()
                ).build();
    }
    
    public static DiagonAlleyGetOrderListResponseDTO convertOrderListToGetOrderListResponseDTO(List<Order> orders) {
        return DiagonAlleyGetOrderListResponseDTO.builder()
                .orders(
                    orders.stream()
                    .map(order -> convertOrderToGetOrderResponseDTO(order))
                    .collect(Collectors.toList())
                ).build();
    }
    
    private static DiagonAlleyGetOrderItemResponseDTO convertOrderItemToGetOrderItemResponseDTO(OrderItem orderItem) {
        return DiagonAlleyGetOrderItemResponseDTO.builder()
                .productId(orderItem.getProductId().toString())
                .quantity(orderItem.getQuantity())
                .build();
    }

    
}
