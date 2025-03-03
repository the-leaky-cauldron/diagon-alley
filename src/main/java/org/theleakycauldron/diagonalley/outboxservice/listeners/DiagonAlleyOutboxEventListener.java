package org.theleakycauldron.diagonalley.outboxservice.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.theleakycauldron.diagonalley.commons.utils.DiagonAlleyUtils;
import org.theleakycauldron.diagonalley.dtos.DiagonAlleyKafkaRequestDTO;
import org.theleakycauldron.diagonalley.outboxservice.dtos.OutboxEventDTO;
import org.theleakycauldron.diagonalley.outboxservice.entities.*;
import org.theleakycauldron.diagonalley.outboxservice.repositories.DiagonAlleyRDBOutboxRepository;

import java.util.concurrent.CompletableFuture;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */

@Slf4j
@Component
public class DiagonAlleyOutboxEventListener {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DiagonAlleyRDBOutboxRepository outboxRepository;

    public DiagonAlleyOutboxEventListener(
            KafkaTemplate<String, String> kafkaTemplate,
            DiagonAlleyRDBOutboxRepository outboxRepository
    ){
        this.kafkaTemplate = kafkaTemplate;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    @EventListener
    public void publishOutboxCreateEvent(OutboxEventDTO outboxEventDTO){
        Outbox outbox = outboxEventDTO.getOutbox();
        boolean isUpdate = outboxEventDTO.isUpdated();
        log.info("Listening to outbox event :: {}", outbox.toString());
        try{
            DiagonAlleyKafkaRequestDTO kafkaRequestDTO = DiagonAlleyUtils.convertProductToKafkaRequestDTO(outbox.getProductJpaEntity());
            CompletableFuture<SendResult<String, String>> kafkaMessage = null;
            if(!isUpdate)
                kafkaMessage = kafkaTemplate.send("diagon-alley-create", kafkaRequestDTO.toString());
            else
                kafkaMessage = kafkaTemplate.send("diagon-alley-update", kafkaRequestDTO.toString());
            kafkaMessage.whenComplete((result, ex) -> {
                if(ex != null)  throw new RuntimeException(ex.getMessage());
            });
            outbox.setPersisted(true);
            outboxRepository.save(outbox);
        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
