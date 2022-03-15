package org.metahut.starfish.ingestion.common;

import org.metahut.starfish.message.api.MessageManager;
import org.metahut.starfish.message.api.MessageType;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

public class MetaMessageProducerTest {

    private static final Logger logger = LoggerFactory.getLogger(MetaMessageProducerTest.class);

    @Test
    public void testServiceLoader() {

        Map<MessageType, MessageManager> managerMap = new HashMap<>();
        ServiceLoader.load(MessageManager.class).forEach(manager -> {

            MessageType type = manager.getType();

            MessageManager messageManager = managerMap.get(type);

            if (Objects.nonNull(messageManager)) {
                throw new IllegalArgumentException(MessageFormat.format("Duplicate message type exists: {0}", type));
            }
            logger.info("type:{}, message manager:{}", type, manager);
            managerMap.put(type, manager);

        });


    }
}
