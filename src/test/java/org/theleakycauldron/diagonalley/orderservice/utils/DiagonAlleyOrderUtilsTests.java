package org.theleakycauldron.diagonalley.orderservice.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderItem;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;

class DiagonAlleyOrderUtilsTests {

    @Test
    void convertOrderToOrderResponseDTO_mapsUuid() {
        Order order = Order.builder()
                .uuid(UUID.fromString("55555555-5555-5555-5555-555555555555"))
                .build();

        assertEquals("55555555-5555-5555-5555-555555555555", org.theleakycauldron.diagonalley.orderservice.utils.DiagonAlleyOrderUtils.convertOrderToOrderResponseDTO(order).getOrderId());
    }

    @Test
    void convertOrderToGetOrderResponseDTO_mapsNestedItems() {
        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .notes("leave at the front desk")
                .trackingNumber("track-123")
                .billAmount(120.0)
                .userId("user-1")
                .orderItems(List.of(
                        OrderItem.builder().productId("product-1").quantity(2).unitPrice(40.0).totalPrice(80.0).build(),
                        OrderItem.builder().productId("product-2").quantity(1).unitPrice(40.0).totalPrice(40.0).build()
                ))
                .build();

        DiagonAlleyGetOrderResponseDTO response = org.theleakycauldron.diagonalley.orderservice.utils.DiagonAlleyOrderUtils.convertOrderToGetOrderResponseDTO(order);

        assertEquals("CREATED", response.getOrderStatus());
        assertEquals("leave at the front desk", response.getNotes());
        assertEquals("track-123", response.getTrackingNumber());
        assertEquals(120.0, response.getBillAmount());
        assertEquals("user-1", response.getUserId());
        assertEquals(2, response.getOrderItems().size());
        assertEquals("product-1", response.getOrderItems().get(0).getProductId());
        assertEquals(2, response.getOrderItems().get(0).getQuantity());
    }

    @Test
    void convertOrderListToGetOrderListResponseDTO_mapsEachOrder() {
        Order first = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .trackingNumber("track-1")
                .billAmount(10.0)
                .userId("user-1")
                .orderItems(List.of(OrderItem.builder().productId("product-1").quantity(1).unitPrice(10.0).totalPrice(10.0).build()))
                .build();
        Order second = Order.builder()
                .orderStatus(OrderStatus.PAYMENT_PENDING)
                .trackingNumber("track-2")
                .billAmount(20.0)
                .userId("user-1")
                .orderItems(List.of(OrderItem.builder().productId("product-2").quantity(2).unitPrice(10.0).totalPrice(20.0).build()))
                .build();

        DiagonAlleyGetOrderListResponseDTO response = org.theleakycauldron.diagonalley.orderservice.utils.DiagonAlleyOrderUtils.convertOrderListToGetOrderListResponseDTO(List.of(first, second));

        assertEquals(2, response.getOrders().size());
        assertEquals("track-1", response.getOrders().get(0).getTrackingNumber());
        assertEquals("track-2", response.getOrders().get(1).getTrackingNumber());
    }
}
