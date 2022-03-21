package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.Collector;
import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.MessageProducer;

public class HiveCollector implements Collector {

    private final HiveParameter hiveParameter;

    private final MessageProducer producer;

    public HiveCollector(HiveParameter hiveParameter) {
        this.hiveParameter = hiveParameter;
        producer = MetaMessageProducer.getInstance();
    }

    public void pull() {

    }

    @Override
    public void close() throws Exception {
        producer.close();
    }
}
