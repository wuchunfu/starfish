package org.metahut.starfish.message.pulsar;

import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClientException;
import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.MessageConsumer;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.metahut.starfish.message.api.MessageException;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PulsarMessageConsumer implements MessageConsumer {

    private final Consumer consumer;

    public PulsarMessageConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public ConsumerResult receive() throws MessageException {
        // Wait for a message
        Message message = null;
        try {
            message = consumer.receive();
            String data = new String(message.getData());
            // Acknowledge the message so that it can be deleted by the message broker
            consumer.acknowledge(message);
            return ConsumerResult.of(consumer.getTopic(), message.getMessageId().toString(), null, message.getKey(), data);
        } catch (PulsarClientException e) {
            // Message failed to process, redeliver later
            if (Objects.nonNull(message)) {
                consumer.negativeAcknowledge(message);
            }
            throw new MessageException(MessageFormat.format("Pulsar consumer receive data exception, topic:{0}, messageId:{1}",
                    consumer.getTopic(), Objects.nonNull(message) ? message.getMessageId().toString() : null), e);
        }

    }

    @Override
    public List<ConsumerResult> batchReceive() throws MessageException {
        List<ConsumerResult> result = new ArrayList<>();
        Messages<byte[]> messages = null;
        try {
            messages = consumer.batchReceive();
            for (Message message : messages) {
                // do something
                String data = new String(message.getData());
                result.add(ConsumerResult.of(consumer.getTopic(), message.getMessageId().toString(), null, message.getKey(), data));
            }
            consumer.acknowledge(messages);
        } catch (PulsarClientException e) {
            // Message failed to process, redeliver later
            if (Objects.nonNull(messages)) {
                consumer.negativeAcknowledge(messages);
            }
            throw new MessageException(MessageFormat.format("Pulsar consumer batch receive data exception, topic:{0}", consumer.getTopic()), e);
        }

        return result;
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(consumer)) {
            consumer.close();
        }
    }
}
