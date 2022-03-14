package org.metahut.starfish.message.pulsar;

import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageProducer;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.metahut.starfish.message.api.MessageResult;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

public class PulsarMessageProducer implements MessageProducer {

    private final Producer producer;

    public PulsarMessageProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public MessageResult send(String key, String value) throws MessageException {
        MessageId messageId = null;
        try {
            messageId = producer.newMessage()
                    .key(key)
                    .value(value.getBytes())
                    .send();
            return MessageResult.of(producer.getTopic(), messageId.toString(), null);
        } catch (PulsarClientException e) {
            throw new MessageException(MessageFormat.format("Pulsar producer send data exception, topic:{0}, messageId:{1}, key:{2}, value:{3}",
                    producer.getTopic(), Objects.nonNull(messageId) ? messageId.toString() : null, key, value), e);
        }
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(producer)) {
            producer.close();
        }

    }
}
