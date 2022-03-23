package org.metahut.starfish.message.api;

import java.util.List;

public interface MessageConsumer extends AutoCloseable {

    List<ConsumerResult> batchReceive() throws MessageException;
}
