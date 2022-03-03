package org.metahut.starfish.message.api;

import java.io.Closeable;

public interface MessageManager extends Closeable {

    MessageProducer getProducer(String name);

    MessageConsumer getConsumer(String name);
}
