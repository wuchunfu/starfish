package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;
import org.metahut.starfish.ingestion.collector.api.JSONUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HiveCollectorParameterTest {

    @Test
    public void testJsonDeserialize() {
        HiveCollectorParameter hiveCollectorParameter = new HiveCollectorParameter();
        hiveCollectorParameter.setType("Hive");
        hiveCollectorParameter.setDatasourceParameter("hive datasource");

        String json = JSONUtils.toJSONString(hiveCollectorParameter);

        AbstractCollectorParameter parameter = JSONUtils.parseObject(json, AbstractCollectorParameter.class);

        Assertions.assertEquals(HiveCollectorParameter.class.getName(), parameter.getClass().getName());

    }
}
