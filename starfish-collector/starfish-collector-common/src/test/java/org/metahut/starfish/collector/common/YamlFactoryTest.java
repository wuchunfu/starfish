package org.metahut.starfish.collector.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YamlFactoryTest {

    private static final Logger logger = LoggerFactory.getLogger(YamlFactoryTest.class);

    @Test
    public void testGet() {
        String key = "starfish.message.pulsar";
        String value = YamlFactory.get(key);
        logger.info("yaml get key:{}, value:{}", key, value);
        Assertions.assertNotNull(value);
    }
}
