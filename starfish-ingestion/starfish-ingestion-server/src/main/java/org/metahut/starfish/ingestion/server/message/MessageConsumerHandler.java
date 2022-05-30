package org.metahut.starfish.ingestion.server.message;

import org.metahut.starfish.ingestion.server.service.MessageService;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageConsumerHandler {

    private final MessageService messageService;

    public MessageConsumerHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostConstruct
    public void metaEventMessageHandler() {
        messageService.toMetaEventConsumer();
    }
}
