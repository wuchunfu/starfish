package org.metahut.starfish.message.pulsar;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.metahut.starfish.message.api.MessageProducer;

import java.io.IOException;
import java.util.Objects;

public class PulsarMessageProducer implements MessageProducer {

    private final Producer producer;

    public PulsarMessageProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void send(String key, String value) {
        try {
            MessageId send = producer.newMessage()
                    .key(key)
                    .value(value.getBytes())
                    .send();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

    }
}
