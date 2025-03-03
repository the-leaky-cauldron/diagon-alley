package org.theleakycauldron.diagonalley.orderservice.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;

@Repository
public interface DiagonAlleyOrderRepository extends JpaRepository<Order, Long> {
    
    Optional<Order> findOrderByUuid(UUID uuid);
    List<Order> findAllOrdersByUserId(String userId);

}
