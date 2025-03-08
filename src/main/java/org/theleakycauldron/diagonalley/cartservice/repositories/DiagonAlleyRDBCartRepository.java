package org.theleakycauldron.diagonalley.cartservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theleakycauldron.diagonalley.cartservice.entities.Cart;

public interface DiagonAlleyRDBCartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByUserId(String userId);
    
}
