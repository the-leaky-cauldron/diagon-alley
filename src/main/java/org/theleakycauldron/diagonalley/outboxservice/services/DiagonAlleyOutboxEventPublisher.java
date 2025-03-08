package org.theleakycauldron.diagonalley.outboxservice.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.theleakycauldron.diagonalley.outboxservice.dtos.*;
import org.theleakycauldron.diagonalley.outboxservice.entities.*;
import org.theleakycauldron.diagonalley.outboxservice.exceptions.*;
import org.theleakycauldron.diagonalley.outboxservice.repositories.*;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Component
public class DiagonAlleyOutboxEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;
    private final DiagonAlleyRDBOutboxRepository diagonAlleyRDBOutboxRepository;

    public DiagonAlleyOutboxEventPublisher(
            DiagonAlleyRDBOutboxRepository diagonAlleyRDBOutboxRepository
    ) {
        this.diagonAlleyRDBOutboxRepository = diagonAlleyRDBOutboxRepository;
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishOutboxEvent(Outbox outbox){

        Optional<Outbox> persistedOutbox = diagonAlleyRDBOutboxRepository.findById(outbox.getId());
        if(persistedOutbox.isEmpty()){
            throw new OutboxNotExistsException("Outbox with id: "+outbox.getId()+" does not exist");
        }
        Outbox savedOutbox = persistedOutbox.get();
        OutboxEventDTO outboxEventDTO = OutboxEventDTO.builder()
                .outbox(savedOutbox)
                .build();
        applicationEventPublisher.publishEvent(outboxEventDTO);
    }

    public void publishOutboxUpdateEvent(UUID uuid){
        Optional<Outbox> persistedOutbox = diagonAlleyRDBOutboxRepository.findByUuid(uuid);
        if(persistedOutbox.isEmpty()){
            throw new OutboxNotExistsException("Outbox with uuid: "+uuid+" does not exist");
        }
        Outbox outbox = persistedOutbox.get();
        outbox.setPersisted(false);
        OutboxEventDTO outboxEventDTO = OutboxEventDTO.builder()
                .outbox(outbox)
                .isUpdated(true)
                .build();
        applicationEventPublisher.publishEvent(outboxEventDTO);
    }

    /**
     * Creates a publish out box event for delete operation
     * 
     * @param uuid
     */

    // TODO: verify this code
    public void publishOutboxDeleteEvent(UUID uuid){
        Optional<Outbox> persistedOutbox = diagonAlleyRDBOutboxRepository.findByUuid(uuid);
        if(persistedOutbox.isEmpty()){
            throw new OutboxNotExistsException("Outbox with uuid: "+uuid+" does not exist");
        }
        Outbox outbox = persistedOutbox.get();
        outbox.setPersisted(false);

        OutboxEventDTO outboxEventDTO = OutboxEventDTO.builder()
                .outbox(outbox)
                .isDeleted(true)
                .build();
        applicationEventPublisher.publishEvent(outboxEventDTO);
    }

}
