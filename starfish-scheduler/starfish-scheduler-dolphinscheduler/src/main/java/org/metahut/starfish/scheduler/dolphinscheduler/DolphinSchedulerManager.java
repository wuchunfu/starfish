package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.ISchedulerManager;
import org.metahut.starfish.scheduler.api.SchedulerProperties;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(prefix = "starfish.scheduler", name = "type", havingValue = "dolphinscheduler")
public class DolphinSchedulerManager implements ISchedulerManager {

    private final DolphinScheduler dolphinScheduler;

    public DolphinSchedulerManager(SchedulerProperties properties) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(6L, TimeUnit.SECONDS);
        builder.readTimeout(6L, TimeUnit.SECONDS);
        builder.writeTimeout(6L, TimeUnit.SECONDS);
        ConnectionPool connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        OkHttpClient client = builder.build();
        dolphinScheduler = new DolphinScheduler(client, properties.getDolphinScheduler());
    }

    @Override
    public DolphinScheduler getScheduler() {
        return dolphinScheduler;
    }

    @Override
    public void close() {
        dolphinScheduler.close();
    }
}
