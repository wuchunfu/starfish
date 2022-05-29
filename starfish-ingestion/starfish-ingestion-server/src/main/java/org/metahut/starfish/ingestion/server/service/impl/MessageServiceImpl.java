package org.metahut.starfish.ingestion.server.service.impl;

import org.metahut.starfish.ingestion.server.service.MessageService;
import org.metahut.starfish.message.api.ConsumerResult;
import org.metahut.starfish.message.api.IMessageConsumer;
import org.metahut.starfish.message.api.IMessageManager;
import org.metahut.starfish.message.api.MessageType;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.row.RowData;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.metahut.starfish.message.api.Constants.MESSAGE_CONSUMER_MAP_KEY_META;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final IMessageManager messageManager;
    private final AbstractMetaDataService<Long, Object> metaDataService;

    public MessageServiceImpl(IMessageManager messageManager, AbstractMetaDataService<Long, Object> metaDataService) {
        this.messageManager = messageManager;
        this.metaDataService = metaDataService;
    }


    @Async("taskExecutor")
    @Override
    public void toMetaEventConsumer() {
        if (MessageType.none == messageManager.getType()) {
            return;
        }
        IMessageConsumer consumer = messageManager.getConsumer(MESSAGE_CONSUMER_MAP_KEY_META);
        if (Objects.isNull(consumer)) {
            throw new NullPointerException("meta event consumer is null");
        }
        while (true) {
            try {
                List<ConsumerResult> result = consumer.batchReceive();
                for (ConsumerResult consumerResult : result) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    RowData<Object> rowData = objectMapper.readValue(consumerResult.getValue(), RowData.class);
                    metaDataService.batchCreateOrUpdate(rowData);
                }
                // TODO Store to metadata

            } catch (Throwable e) {
                // TODO How to consume or handle exceptions
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
