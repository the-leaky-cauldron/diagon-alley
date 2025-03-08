package org.theleakycauldron.diagonalley.outboxservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.theleakycauldron.diagonalley.outboxservice.entities.Outbox;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Repository
public interface DiagonAlleyRDBOutboxRepository extends JpaRepository<Outbox, Long> {
    Optional<Outbox> findByUuid(UUID uuid);
}
