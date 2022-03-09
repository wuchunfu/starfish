package org.metahut.starfish.collector.common;

import org.metahut.starfish.message.api.MessageProducer;

public class MetaMessageProducer {

    static {
        // 创建生产者


    }

    // 发送消息方法


    private static enum Singleton {
        INSTANCE;
        private MessageProducer producer;

        private Singleton() {


        }
    }
}
