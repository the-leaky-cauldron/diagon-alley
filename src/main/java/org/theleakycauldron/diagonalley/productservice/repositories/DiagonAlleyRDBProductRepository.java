package org.theleakycauldron.diagonalley.productservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.theleakycauldron.diagonalley.productservice.entities.ProductJpaEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Repository
public interface DiagonAlleyRDBProductRepository extends JpaRepository<ProductJpaEntity, Long> {
    Optional<ProductJpaEntity> findByName(String name);
    Optional<ProductJpaEntity> findByUuid(UUID uuid);
}
