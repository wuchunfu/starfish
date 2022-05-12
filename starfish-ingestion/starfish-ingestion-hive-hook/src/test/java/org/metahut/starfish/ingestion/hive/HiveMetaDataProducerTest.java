package org.metahut.starfish.ingestion.hive;

import org.metahut.starfish.ingestion.common.MetaMessageProducer;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.message.api.MessageException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HiveMetaDataProducerTest {

    @Test
    @Disabled
    public void test() throws MessageException {
        IMessageProducer instance = MetaMessageProducer.getInstance();
        instance.send("test", "test");
    }


}
