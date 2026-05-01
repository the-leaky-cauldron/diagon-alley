package org.theleakycauldron.diagonalley.orderservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyCreateOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderItem;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;
import org.theleakycauldron.diagonalley.orderservice.exceptions.CannotModifyDeletedOrderException;
import org.theleakycauldron.diagonalley.orderservice.exceptions.OrderNotFoundException;
import org.theleakycauldron.diagonalley.orderservice.repositories.DiagonAlleyOrderRepository;

@ExtendWith(MockitoExtension.class)
class DiagonAlleyOrderServiceImplTests {

    @Mock
    private DiagonAlleyOrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private DiagonAlleyOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new DiagonAlleyOrderServiceImpl(orderRepository, kafkaTemplate);
    }

    @Test
    void createOrder_savesOrderAndMapsUuid() {
        Order order = Order.builder()
                .uuid(UUID.fromString("66666666-6666-6666-6666-666666666666"))
                .build();

        when(orderRepository.save(order)).thenReturn(order);

        DiagonAlleyCreateOrderResponseDTO response = service.createOrder(order);

        assertEquals("66666666-6666-6666-6666-666666666666", response.getOrderId());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_updatesOrderAndPublishesKafkaEvent() {
        Order order = Order.builder()
                .uuid(UUID.fromString("77777777-7777-7777-7777-777777777777"))
                .userId("user-1")
                .orderStatus(OrderStatus.CREATED)
                .isDeleted(false)
                .build();
        when(orderRepository.findOrderByUuid(order.getUuid())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        @SuppressWarnings({"rawtypes", "unchecked"})
        CompletableFuture<SendResult<String, String>> future = CompletableFuture.completedFuture((SendResult) null);
        when(kafkaTemplate.send(eq("order-status-changed"), any(String.class))).thenReturn(future);

        service.updateOrderStatus(order.getUuid().toString(), OrderStatus.PAYMENT_PENDING);

        assertEquals(OrderStatus.PAYMENT_PENDING, order.getOrderStatus());
        assertNotNull(order.getUpdatedAt());
        verify(orderRepository).save(order);

        ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(eq("order-status-changed"), payloadCaptor.capture());
        assertEquals(true, payloadCaptor.getValue().contains("PAYMENT_PENDING"));
        assertEquals(true, payloadCaptor.getValue().contains("user-1"));
    }

    @Test
    void updateOrderStatus_throwsWhenOrderMissing() {
        UUID orderId = UUID.fromString("88888888-8888-8888-8888-888888888888");
        when(orderRepository.findOrderByUuid(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> service.updateOrderStatus(orderId.toString(), OrderStatus.PAYMENT_PENDING));
        verify(orderRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(any(String.class), any(String.class));
    }

    @Test
    void updateOrderStatus_throwsWhenOrderIsDeleted() {
        Order order = Order.builder()
                .uuid(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                .isDeleted(true)
                .build();
        when(orderRepository.findOrderByUuid(order.getUuid())).thenReturn(Optional.of(order));

        assertThrows(CannotModifyDeletedOrderException.class, () -> service.updateOrderStatus(order.getUuid().toString(), OrderStatus.PAYMENT_PENDING));
        verify(orderRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(any(String.class), any(String.class));
    }

    @Test
    void deleteOrder_marksOrderAsDeleted() {
        Order order = Order.builder()
                .uuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .isDeleted(false)
                .build();
        when(orderRepository.findOrderByUuid(order.getUuid())).thenReturn(Optional.of(order));

        service.deleteOrder(order.getUuid().toString());

        assertEquals(true, order.isDeleted());
        assertNotNull(order.getUpdatedAt());
    }

    @Test
    void getOrder_mapsPersistedOrder() {
        Order order = Order.builder()
                .uuid(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .orderStatus(OrderStatus.CREATED)
                .trackingNumber("track-1")
                .billAmount(50.0)
                .userId("user-1")
                .orderItems(List.of(OrderItem.builder().productId("product-1").quantity(1).unitPrice(50.0).totalPrice(50.0).build()))
                .build();
        when(orderRepository.findOrderByUuid(order.getUuid())).thenReturn(Optional.of(order));

        DiagonAlleyGetOrderResponseDTO response = service.getOrder(order.getUuid().toString());

        assertEquals("CREATED", response.getOrderStatus());
        assertEquals("track-1", response.getTrackingNumber());
        assertEquals(50.0, response.getBillAmount());
        assertEquals("user-1", response.getUserId());
    }

    @Test
    void getOrder_throwsWhenMissing() {
        UUID orderId = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
        when(orderRepository.findOrderByUuid(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> service.getOrder(orderId.toString()));
    }

    @Test
    void getOrders_filtersDeletedOrders() {
        Order activeOrder = Order.builder()
                .uuid(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd"))
                .orderStatus(OrderStatus.CREATED)
                .trackingNumber("track-active")
                .billAmount(25.0)
                .userId("user-1")
                .isDeleted(false)
            .orderItems(List.of(OrderItem.builder().productId("product-active").quantity(1).unitPrice(25.0).totalPrice(25.0).build()))
                .build();
        Order deletedOrder = Order.builder()
                .uuid(UUID.fromString("eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee"))
                .orderStatus(OrderStatus.CREATED)
                .trackingNumber("track-deleted")
                .billAmount(30.0)
                .userId("user-1")
                .isDeleted(true)
            .orderItems(List.of(OrderItem.builder().productId("product-deleted").quantity(1).unitPrice(30.0).totalPrice(30.0).build()))
                .build();
        when(orderRepository.findAllOrdersByUserId("user-1")).thenReturn(List.of(activeOrder, deletedOrder));

        DiagonAlleyGetOrderListResponseDTO response = service.getOrders("user-1");

        assertEquals(1, response.getOrders().size());
        assertEquals("track-active", response.getOrders().get(0).getTrackingNumber());
        assertFalse(response.getOrders().get(0).getOrderItems() == null);
    }

    @Test
    void getOrders_throwsWhenNoOrdersExist() {
        when(orderRepository.findAllOrdersByUserId("user-1")).thenReturn(List.of());

        assertThrows(OrderNotFoundException.class, () -> service.getOrders("user-1"));
    }
}
