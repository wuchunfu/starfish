package org.metahut.starfish.message.api;

import java.io.Closeable;

public interface MessageManager extends Closeable {

    MessageType getType();

    void init(MessageProperties messageProperties) throws MessageException;

    MessageProducer getProducer(String name);

    MessageConsumer getConsumer(String name);
}
