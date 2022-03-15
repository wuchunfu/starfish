package org.metahut.starfish.message.api;

import org.springframework.lang.Nullable;

public class MessageResult {

    private String topic;
    private String offset;
    private String partition;

    public static MessageResult of(String topic, String offset, @Nullable String partition) {
        return new MessageResult(topic, offset, partition);
    }

    public MessageResult() {
    }

    public MessageResult(String topic, String offset, String partition) {
        this.topic = topic;
        this.offset = offset;
        this.partition = partition;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }
}
