package org.metahut.starfish.message.api;

public interface MessageProducer extends AutoCloseable {

    MessageResult send(String key, String value) throws MessageException;
}
