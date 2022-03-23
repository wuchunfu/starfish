package org.metahut.starfish.message.api;

public interface MessageManager extends AutoCloseable {

    MessageType getType();

    void init(MessageProperties messageProperties) throws MessageException;

    MessageProducer getProducer(String name);

    MessageConsumer getConsumer(String name);
}
