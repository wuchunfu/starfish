package org.metahut.starfish.scheduler.dolphinscheduler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.HttpParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.TaskDefinitionParameter;

import java.io.IOException;

public class DolphinScheduler implements IScheduler {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private static final String HEADER_TOKEN_NAME = "token";

    private final OkHttpClient client;
    private final SchedulerProperties.DolphinScheduler properties;

    public DolphinScheduler(OkHttpClient client, SchedulerProperties.DolphinScheduler properties) {
        this.client = client;
        this.properties = properties;
    }

    public void createTask() {

        // 入参不一样

    }

    public void createSchedule(String cron) {


    }

    // 创建任务

    // 设置定时

    // 测试任务

    // 创建单个 Http 任务

    @Override
    public void createSingleHttpTask(HttpTaskParameter httpTaskParameter) {
        HttpParameter parameter = new HttpParameter();

        TaskDefinitionParameter taskDefinitionParameter = new TaskDefinitionParameter();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    @Override
    public void close() {

    }

}
