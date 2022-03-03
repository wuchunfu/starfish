package org.metahut.starfish.message.api;

import java.io.Closeable;

public interface MessageProducer extends Closeable {
    void send(String key, String value);
}
