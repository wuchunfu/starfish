package org.metahut.starfish.ingestion.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.metahut.starfish.ingestion.common.Constants.INGESTION_CONFIG_FILE;
import static org.metahut.starfish.ingestion.common.Constants.META_CONFIG_PREFIX;

public class MetaClientTest {

    @Test
    public void testInit() {
        IngestionProperties properties = YamlFactory.parseObject(META_CONFIG_PREFIX, INGESTION_CONFIG_FILE, new IngestionProperties());
        IngestionProperties.MetaMessageProperties messageProperties = properties.getMessage();
        Assertions.assertNotNull(messageProperties);
    }
}
