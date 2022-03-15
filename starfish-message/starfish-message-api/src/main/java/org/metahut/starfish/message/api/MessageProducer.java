package org.metahut.starfish.message.api;

import java.io.Closeable;

public interface MessageProducer extends Closeable {

    MessageResult send(String key, String value) throws MessageException;
}
