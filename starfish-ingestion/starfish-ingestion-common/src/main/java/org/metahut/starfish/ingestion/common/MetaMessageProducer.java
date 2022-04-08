package org.metahut.starfish.ingestion.common;

import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageManager;
import org.metahut.starfish.message.api.MessageProducer;
import org.metahut.starfish.message.api.MessageProperties;
import org.metahut.starfish.message.api.MessageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.ServiceLoader;

import static org.metahut.starfish.ingestion.common.Constants.INGESTION_CONFIG_FILE;
import static org.metahut.starfish.message.api.Constants.MESSAGE_CONFIG_PREFIX;
import static org.metahut.starfish.message.api.Constants.MESSAGE_META_EVENT;

public class MetaMessageProducer {

    public static final Logger logger = LoggerFactory.getLogger(MetaMessageProducer.class);

    private MetaMessageProducer() {

    }

    public static MessageProducer getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;

        private MessageProducer producer;

        private MessageManager messageManager;

        private Singleton() {
            init();
            if (Objects.nonNull(messageManager)) {
                throw new RuntimeException("meta message manager init exception");
            }
            producer = messageManager.getProducer(MESSAGE_META_EVENT);
            if (Objects.nonNull(producer)) {
                throw new RuntimeException("meta message producer init exception");
            }
        }

        public MessageProducer getInstance() {
            return producer;
        }

        private void init() {
            MessageProperties messageProperties = YamlFactory.parseObject(MESSAGE_CONFIG_PREFIX, INGESTION_CONFIG_FILE, new MessageProperties());
            ServiceLoader.load(MessageManager.class).forEach(manager -> {
                MessageType type = manager.getType();
                if (messageProperties.getType() == type) {
                    messageManager = manager;
                    try {
                        messageManager.init(messageProperties);
                    } catch (MessageException e) {
                        logger.error("message init exception, message:{}", e);
                    }
                    return;
                }
            });
        }
    }
}
