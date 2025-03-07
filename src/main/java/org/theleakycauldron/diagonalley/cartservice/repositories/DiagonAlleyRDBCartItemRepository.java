package org.theleakycauldron.diagonalley.cartservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.theleakycauldron.diagonalley.cartservice.entities.CartItem;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
public interface DiagonAlleyRDBCartItemRepository extends JpaRepository<CartItem, Long> {
}
