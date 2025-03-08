package org.theleakycauldron.diagonalley.orderservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyCreateOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderListResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.dtos.DiagonAlleyGetOrderResponseDTO;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;
import org.theleakycauldron.diagonalley.orderservice.exceptions.CannotModifyDeletedOrderException;
import org.theleakycauldron.diagonalley.orderservice.exceptions.OrderNotFoundException;
import org.theleakycauldron.diagonalley.orderservice.repositories.DiagonAlleyOrderRepository;
import org.theleakycauldron.diagonalley.orderservice.utils.DiagonAlleyOrderUtils;

@Service
public class DiagonAlleyOrderServiceImpl implements DiagonAlleyOrderService{

    private final DiagonAlleyOrderRepository orderRepository;

    public DiagonAlleyOrderServiceImpl(DiagonAlleyOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public DiagonAlleyCreateOrderResponseDTO createOrder(Order order) {

        Order savedOrder = orderRepository.save(order);

        return DiagonAlleyOrderUtils.convertOrderToOrderResponseDTO(savedOrder);

    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus orderStatus) {
        
        LocalDateTime now = LocalDateTime.now();

        Optional<Order> persistedOrderOptional = orderRepository.findOrderByUuid(UUID.fromString(orderId));

        if(persistedOrderOptional.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }

        Order persistedOrder = persistedOrderOptional.get();
        
        if(persistedOrder.isDeleted()) {
            throw new CannotModifyDeletedOrderException("Cannot modify deleted order");
        }

        persistedOrder.setUpdatedAt(now);
        persistedOrder.setOrderStatus(orderStatus);


        orderRepository.save(persistedOrder);


    }

    @Override
    public void deleteOrder(String uuid) {
        Optional<Order> persistedOrder = orderRepository.findOrderByUuid(UUID.fromString(uuid));

        LocalDateTime now = LocalDateTime.now();

        if(persistedOrder.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }

        Order order = persistedOrder.get();
        order.setUpdatedAt(now);
        order.setDeleted(true);
    }

    @Override
    public DiagonAlleyGetOrderResponseDTO getOrder(String uuid) {
        
        Optional<Order> persistedOrder = orderRepository.findOrderByUuid(UUID.fromString(uuid));

        if(persistedOrder.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }

        Order order = persistedOrder.get();

        return DiagonAlleyOrderUtils.convertOrderToGetOrderResponseDTO(order);

    }

    @Override
    public DiagonAlleyGetOrderListResponseDTO getOrders(String userId) {
    
        List<Order> orders = orderRepository.findAllOrdersByUserId(userId);
        if(orders.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }

        orders = orders.stream().filter(order -> !order.isDeleted()).toList();

        return DiagonAlleyOrderUtils.convertOrderListToGetOrderListResponseDTO(orders);

    }
    
}
