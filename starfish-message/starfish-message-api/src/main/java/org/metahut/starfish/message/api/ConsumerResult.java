package org.metahut.starfish.message.api;

import org.springframework.lang.Nullable;

public class ConsumerResult extends MessageResult {

    private String key;
    private String value;

    public static ConsumerResult of(String topic, String offset, @Nullable String partition) {
        return new ConsumerResult(topic, offset, partition, null, null);
    }

    public static ConsumerResult of(String topic, String offset, @Nullable String partition, @Nullable String key, String value) {
        return new ConsumerResult(topic, offset, partition, key, value);
    }

    public ConsumerResult(String topic, String offset, String partition, String key, String value) {
        super(topic, offset, partition);
        this.key = key;
        this.value = value;
    }

    public ConsumerResult() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
