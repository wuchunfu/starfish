package org.metahut.starfish.message.api;

import java.io.Closeable;
import java.util.List;

public interface MessageConsumer extends Closeable {

    List<ConsumerResult> batchReceive() throws MessageException;
}
