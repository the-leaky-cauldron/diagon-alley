package org.theleakycauldron.diagonalley.outboxservice.dtos;

import org.theleakycauldron.diagonalley.outboxservice.entities.Outbox;

import lombok.*;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OutboxEventDTO {
    private Outbox outbox;
    private boolean isUpdated;
    private boolean isDeleted;
}
