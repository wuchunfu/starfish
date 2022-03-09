package org.metahut.starfish.message.api;

import java.io.Closeable;

public interface MessageConsumer extends Closeable {

    String receive() throws Exception;
}
