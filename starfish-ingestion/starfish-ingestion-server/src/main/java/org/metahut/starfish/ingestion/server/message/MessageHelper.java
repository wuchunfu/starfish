package org.metahut.starfish.ingestion.server.message;

import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.MessageConsumer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class MessageHelper {

    private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    private final MessageManager messageManager;

    public MessageHelper(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    @PostConstruct
    private void autoConsumer() {
        autoMetaEventConsumer();
    }

    @Async
    public void autoMetaEventConsumer() {
        MessageConsumer consumer = messageManager.getConsumer("");
        // TODO
        if (Objects.isNull(consumer)) {
            return;
        }

        while (true) {
            try {
                List<ConsumerResult> result = consumer.batchReceive();

                // TODO Store to metadata

            } catch (MessageException e) {
                // TODO How to consume or handle exceptions
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void close() throws IOException {
        messageManager.close();
    }
}
