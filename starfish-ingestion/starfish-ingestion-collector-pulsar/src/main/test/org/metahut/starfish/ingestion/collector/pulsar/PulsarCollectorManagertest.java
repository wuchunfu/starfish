package org.metahut.starfish.ingestion.collector.pulsar;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.metahut.starfish.ingestion.collector.pulsar.PulsarCollectorManager;

public class PulsarCollectorManagertest {

    String url = "XXXXX";

    @Test
    @Disabled
    public void getpulsar() {
        PulsarCollectorManager pulsarCollectorManager = new PulsarCollectorManager();
        pulsarCollectorManager.generateInstance(url).execute();
    }
}
