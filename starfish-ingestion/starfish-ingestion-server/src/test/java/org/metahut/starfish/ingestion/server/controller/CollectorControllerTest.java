package org.metahut.starfish.ingestion.server.controller;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;
import org.metahut.starfish.ingestion.collector.hive.HiveCollectorParameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CollectorControllerTest {

    @Test
    public void testCollectorParameterDeserialize() {
        String parameterJson = "{\"type\":\"Hive\",\"datasourceId\":\"hive idc1\"}";
        AbstractCollectorParameter parameter = JSONUtils.parseObject(parameterJson, AbstractCollectorParameter.class);
        Assertions.assertEquals(HiveCollectorParameter.class.getName(), parameter.getClass().getName());
        parameterJson = "{\"type\":\"Hive1\",\"datasourceId\":\"hive idc1\"}";

        parameter = JSONUtils.parseObject(parameterJson, AbstractCollectorParameter.class);
        Assertions.assertNull(parameter);
    }
}
