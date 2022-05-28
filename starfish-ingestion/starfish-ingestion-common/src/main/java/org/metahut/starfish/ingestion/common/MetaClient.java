package org.metahut.starfish.ingestion.common;

import org.metahut.starfish.message.api.IMessageManager;
import org.metahut.starfish.message.api.IMessageProducer;
import org.metahut.starfish.message.api.MessageException;
import org.metahut.starfish.message.api.MessageProperties;

import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.TimeUnit;

import static org.metahut.starfish.ingestion.common.Constants.INGESTION_CONFIG_FILE;
import static org.metahut.starfish.ingestion.common.Constants.META_CONFIG_PREFIX;
import static org.metahut.starfish.message.api.Constants.MESSAGE_PRODUCER;

public class MetaClient {

    private static final Logger logger = LoggerFactory.getLogger(MetaClient.class);

    private static class MetaClientHolder {
        private static final MetaClient INSTANCE = new MetaClient();
    }

    public static MetaClient getInstance() {
        return MetaClientHolder.INSTANCE;
    }

    private IngestionProperties.RestProperties restProperties;
    private IMessageProducer producer;

    private OkHttpClient okHttpClient;

    private MetaClient() {
        IngestionProperties properties = YamlFactory.parseObject(META_CONFIG_PREFIX, INGESTION_CONFIG_FILE, new IngestionProperties());
        initMessage(properties.getMessage());

        restProperties = properties.getRest();
        initRest(restProperties);
    }

    public IMessageProducer getMessageProducer() {
        return producer;
    }

    public void queryMetaData() throws IOException {
        // TODO query metadata rest
        String url = restProperties.getQueryMetadataApi();
        String s = get(url);
    }

    private void initMessage(MessageProperties messageProperties) {
        for (IMessageManager messageManager : ServiceLoader.load(IMessageManager.class)) {
            if (messageProperties.getType() == messageManager.getType()) {
                try {
                    messageManager.init(messageProperties);
                } catch (MessageException e) {
                    logger.error("message init exception, message:{}", e);
                }

                if (Objects.isNull(messageManager)) {
                    throw new RuntimeException("meta message manager create exception");
                }
                producer = messageManager.getProducer(MESSAGE_PRODUCER);
                if (Objects.isNull(producer)) {
                    throw new RuntimeException("meta message producer create exception");
                }
                break;
            }
        }
    }

    private void initRest(IngestionProperties.RestProperties properties) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(6L, TimeUnit.SECONDS);
        builder.readTimeout(6L, TimeUnit.SECONDS);
        builder.writeTimeout(6L, TimeUnit.SECONDS);
        ConnectionPool connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        okHttpClient = builder.build();
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
