package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.SchedulerException;
import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.SchedulerResult;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DolphinScheduler implements IScheduler {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private static final String HEADER_TOKEN_NAME = "token";
    private static final String METHOD_TYPE = "post";
    private static final int HTTP_SUCCESS = 0;

    private final OkHttpClient client;
    private final SchedulerProperties.DolphinScheduler properties;

    public DolphinScheduler(OkHttpClient client, SchedulerProperties.DolphinScheduler properties) {
        this.client = client;
        this.properties = properties;
    }

    static final HashMap<String, String> formTaskMap = new HashMap<>();
    static final HashMap<String, String> onLineMap = new HashMap<>();
    static HashMap<String, String> schedulerMap = new HashMap<>();
    static HashMap<String, String> deleteMap = new HashMap<>();
    static HashMap<String, String> putMap = new HashMap<>();
    static HashMap<String, String> putScheduleMap = new HashMap<>();

    static {
        formTaskMap.put("taskDefinitionJson", "[{\"code\":5028755940768,\"name\":\"test_http1\",\"version\":1,\"description\":"
                + "\"\",\"delayTime\":0,\"taskType\":\"HTTP\",\"taskParams\":{\"localParams\":[],\"httpParams\":[],\"url\":\"http:1\""
                + ",\"httpMethod\":\"GET\",\"httpCheckCondition\":\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\":60000,"
                + "\"socketTimeout\":60000,\"dependence\":{},\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"waitStartTimeout\":{},"
                + "\"switchResult\":{}},\"flag\":\"YES\",\"taskPriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"failRetryTimes\":0,"
                + "\"failRetryInterval\":1,\"timeoutFlag\":\"CLOSE\",\"timeoutNotifyStrategy\":null,\"timeout\":0,\"environmentCode\":-1}]");
        formTaskMap
                .put("locations", "[{\"taskCode\":5028755940768,\"x\":115,\"y\":270.0000305175781}]");
        formTaskMap.put("name", "test_http2");
        formTaskMap.put("taskRelationJson",
                "[{\"name\":\"\",\"preTaskCode\":0,\"preTaskVersion\":0,\"postTaskCode\":5028755940768,\"postTaskVersion\":0,\"conditionType\":0,\"conditionParams\":{}}]");
        formTaskMap.put("tenantCode", "default");
        formTaskMap.put("description", "aa1");
        formTaskMap.put("globalParams", "[]");
        formTaskMap.put("timeout", "0");

        onLineMap.put("name", "aa2");
        onLineMap.put("releaseState", "ONLINE");

        schedulerMap.put("schedule",
                "{\"startTime\":\"2022-03-30 00:00:00\",\"endTime\":\"2122-03-30 00:00:00\",\"crontab\":\"0 0 * * * ? *\",\"timezoneId\":\"Asia/Shanghai\"}");

        deleteMap.put("code", "5028829411360");

        putMap.put("name", "test_http3");
        putMap.put("locations",
                "[{\"taskCode\":5028755940768,\"x\":120,\"y\":270},{\"taskCode\":5028755940768,\"x\":120,\"y\":416.0000305175781}]");
        putMap.put("taskDefinitionJson",
                "[{\"code\":5028755940768,\"name\":\"test_http1\",\"version\":1,"
                        + "\"description\":\"\",\"delayTime\":0,\"taskType\":\"HTTP\","
                        + "\"taskParams\":{\"localParams\":[],\"httpParams\":[],\"url\""
                        + ":\"http:1\",\"httpMethod\":\"GET\",\"httpCheckCondition\":"
                        + "\"STATUS_CODE_DEFAULT\",\"condition\":\"\",\"connectTimeout\""
                        + ":60000,\"socketTimeout\":60000,\"dependence\":{},"
                        + "\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"waitStartTimeout\":{},\"switchResult\":{}},"
                        + "\"flag\":\"YES\",\"taskPriority\":\"MEDIUM\",\"workerGroup\":\"default\",\"failRetryTimes\":0,\"failRetryInterval\":1,"
                        + "\"timeoutFlag\":\"CLOSE\",\"timeoutNotifyStrategy\":null,\"timeout\":0,\"environmentCode\":-1}]");
        putMap.put("taskRelationJson", "[{\"name\":\"\",\"preTaskCode\":0,\"preTaskVersion\":0,\"postTaskCode\":5028755940768,\"postTaskVersion\":1,\"conditionType\":0,\"conditionParams\":{}}]");
        putMap.put("tenantCode", "algdataonline");
        putMap.put("description", "aa1");

        putScheduleMap.put("environmentCode", "");
        putScheduleMap.put("failureStrategy", "CONTINUE");
        putScheduleMap.put("processInstancePriority", "MEDIUM");
        putScheduleMap.put("schedule",
                "{\"startTime\":\"2022-03-30 00:00:00\",\"endTime\":\"2122-03-30 00:00:00\",\"crontab\":\"0 0 * * * ? *\",\"timezoneId\":\"Asia/Shanghai\"}");
        putScheduleMap.put("warningType", "NONE");
        putScheduleMap.put("warningGroupId", "1");
        putScheduleMap.put("workerGroup", "default");

    }

    @Override
    public List<String> previewSchedule(ScheduleParameter scheduleParameter) {
        String url = MessageFormat.format("/projects/{0}/schedules/preview", properties.getProjectCode());
        FormBody body = new Builder().add("schedule", JSONUtils.toJSONString(scheduleParameter)).build();
        try {
            String resultJson = post(url, body);
            DolphinResult<List<String>> result = JSONUtils.parseObject(resultJson, new TypeReference<DolphinResult<List<String>>>() {});
            checkResult(result, "previewSchedule");
            return result.getData();
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call previewSchedule method exception", e);
        }
    }

    private void checkResult(DolphinResult result, String method) {
        if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
            throw new SchedulerException(MessageFormat.format("dolphin scheduler call {0} method exception, {1}", method, Objects.isNull(result) ? "result is empty" : result.getMsg()));
        }
    }

    @Override
    public SchedulerResult createSingleHttpTask(HttpTaskParameter httpTaskParameter) {
        return null;

    }

    public SchedulerResult createSingleHttpTask1(HttpTaskParameter httpTaskParameter) {
        String processJson = null;
        DolphinResult processResult = null;
        String releaseJson = null;
        DolphinResult releaseResult = null;
        String cronJson = null;
        DolphinResult cronResult = null;
        if (METHOD_TYPE == httpTaskParameter.getMethod()) {
            if (StringUtils.isNotBlank(httpTaskParameter.getBody()) && StringUtils
                    .isNotBlank(httpTaskParameter.getUrl())) {
                try {
                    processJson = formPost(
                            "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition",
                            formTaskMap);
                    processResult = JSONUtils.parseObject(processJson, DolphinResult.class);
                    if (Objects.isNull(processResult)) {
                        return new SchedulerResult(false, "create task is error");
                    } else if (HTTP_SUCCESS == processResult.getCode()) {
                        HashMap processData = (HashMap) processResult.getData();
                        releaseJson = formPost(
                                "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition/"
                                        + processData.get("code")
                                        + "/release",
                                onLineMap);
                        releaseResult = JSONUtils.parseObject(releaseJson, DolphinResult.class);
                        if (Objects.isNull(releaseResult)) {
                            return new SchedulerResult(false, "update online is error");
                        } else if (HTTP_SUCCESS == releaseResult.getCode()) {
                            //设置定时
                            schedulerMap
                                    .put("processDefinitionCode", processData.get("code").toString());
                            cronJson = formPost(
                                    "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/schedules",
                                    schedulerMap);
                            cronResult = JSONUtils.parseObject(cronJson, DolphinResult.class);
                        }
                    }
                } catch (IOException e) {
                    throw new SchedulerException("http request send is fail", e);
                }
            }
            if (Objects.isNull(cronResult) || HTTP_SUCCESS != cronResult.getCode()) {
                return new SchedulerResult(false, "set schedular is error");
            }
            return new SchedulerResult(true, "create task is success");
        }
        return new SchedulerResult(false, "please comfirm method type");
    }

    @Override
    public Object queryTaskDefinitionPageList() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition?pageNo=1&pageSize=10&&searchVal=";
        DolphinResult result = null;
        try {
            String json = get(url);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "don't get anything of task definitions");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object queryTaskDefinitionByCode() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition/5028876785952";
        DolphinResult result = null;
        String cronJson = null;
        DolphinResult cronResult = null;
        try {
            String json = get(url);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "don't get anything of task definition detail");
            }
            //获取定时管理
            schedulerMap
                    .put("processDefinitionCode", "5017950541088");
            cronJson = get(
                    "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/schedules?processDefinitionCode=5028876785952&searchVal=&pageNo=1&pageSize=10");
            cronResult = JSONUtils.parseObject(cronJson, DolphinResult.class);
            result.setData(result.getData().toString() + cronResult.getData().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object queryTaskDetailLogs() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/log/detail?taskInstanceId=128460&skipLineNum=1&limit=10";
        DolphinResult result = null;
        try {
            String json = get(url);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "don't get anything of task definitions' logs");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object queryTaskInstanceLogs() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/task-instances?"
                + "pageSize=10&pageNo=1&searchVal=&processInstanceId=&host=&stateType=&startDate=&endDate=&executorName=&processInstanceName=";
        DolphinResult result = null;
        try {
            String json = get(url);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "don't get anything of task definitions' logs");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object deleteTaskDefinitionByCode() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition/5028829411360";
        DolphinResult result = null;
        try {
            String json = delete(url, deleteMap);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "delete processdefinition is error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Object updateTaskDefinition() {
        String url = "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/process-definition/5028876785952";
        DolphinResult result = null;
        String cronJson = null;
        DolphinResult cronResult = null;
        try {
            String json = put(url, putMap);
            result = JSONUtils.parseObject(json, DolphinResult.class);
            if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
                return new SchedulerResult(false, "delete processdefinition is error");
            }
            schedulerMap
                    .put("processDefinitionCode", "5017950541088");
            cronJson = put(
                    "http://dolphinscheduler.dev.zhaopin.com/dolphinscheduler/projects/4996418468000/schedules/28",
                    putScheduleMap);
            cronResult = JSONUtils.parseObject(cronJson, DolphinResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cronResult;
    }

    public String get(String url) throws IOException {
        url = properties.getServiceUrl() + url;
        Request request = new Request.Builder()
                .url(url)
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String formPost(String url, Map<String, String> params) throws IOException {
        Builder formBody = new Builder();
        params.entrySet().stream().forEach(map -> {
            formBody.add(map.getKey(), map.getValue());
        });
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .addHeader("Content-type", "application/x-www-form-urlencoded")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * Form post request
     * @param url url
     * @param body form body
     * @return json response
     * @throws IOException
     */
    public String post(String url, FormBody body) throws IOException {
        url = properties.getServiceUrl() + url;
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String post(String url, String json) throws IOException {
        url = properties.getServiceUrl() + url;
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

    public String delete(String url, Map<String, String> params) throws IOException {
        Builder deleteBody = new Builder();
        params.entrySet().stream().forEach(map -> {
            deleteBody.add(map.getKey(), map.getValue());
        });
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader(HEADER_TOKEN_NAME, properties.getToken())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String put(String url, Map<String, String> params) throws IOException {
        Builder putBody = new Builder();
        params.entrySet().stream().forEach(map -> {
            putBody.add(map.getKey(), map.getValue());
        });
        Request request = new Request.Builder()
                .url(url)
                .put(putBody.build())
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
