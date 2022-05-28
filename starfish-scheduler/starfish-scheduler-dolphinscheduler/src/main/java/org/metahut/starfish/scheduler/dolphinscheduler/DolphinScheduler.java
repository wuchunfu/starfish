package org.metahut.starfish.scheduler.dolphinscheduler;

import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.PageResponse;
import org.metahut.starfish.scheduler.api.SchedulerException;
import org.metahut.starfish.scheduler.api.SchedulerProperties;
import org.metahut.starfish.scheduler.api.entity.FlowDefinition;
import org.metahut.starfish.scheduler.api.entity.FlowInstance;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.PageRequest;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.entity.ComplexProcessDefinition;
import org.metahut.starfish.scheduler.dolphinscheduler.entity.ProcessDefinition;
import org.metahut.starfish.scheduler.dolphinscheduler.entity.ProcessInstance;
import org.metahut.starfish.scheduler.dolphinscheduler.entity.Schedule;
import org.metahut.starfish.scheduler.dolphinscheduler.enums.ReleaseState;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.HttpParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.HttpParametersType;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.HttpProperty;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.ProcessDAGLocationParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.ProcessTaskRelationParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.parameter.TaskDefinitionParameter;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DolphinScheduler implements IScheduler {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private static final String HEADER_TOKEN_NAME = "token";
    private static final int HTTP_SUCCESS = 0;

    private final OkHttpClient client;
    private final SchedulerProperties.DolphinScheduler properties;

    public DolphinScheduler(OkHttpClient client, SchedulerProperties.DolphinScheduler properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public List<String> previewSchedule(ScheduleCronParameter scheduleCronParameter) {
        setScheduleCronDefaultValue(scheduleCronParameter);
        String url = MessageFormat.format("/projects/{0}/schedules/preview", properties.getProjectCode());
        FormBody body = new FormBody.Builder()
                .add("schedule", JSONUtils.toJSONString(scheduleCronParameter))
                .build();
        try {
            String resultJson = post(url, body);
            DolphinResult<List<String>> result = JSONUtils.parseObject(resultJson, new TypeReference<DolphinResult<List<String>>>() {});
            checkResult(result, "previewSchedule");
            return result.getData();
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call previewSchedule method exception", e);
        }
    }

    /**
     * set schedule cron default value
     * @param scheduleCronParameter schedule cron parameter
     */
    private void setScheduleCronDefaultValue(ScheduleCronParameter scheduleCronParameter) {
        if (Objects.isNull(scheduleCronParameter.getStartTime())) {
            scheduleCronParameter.setStartTime(new Date());
        }
        if (Objects.isNull(scheduleCronParameter.getEndTime())) {
            // + 100 year
            scheduleCronParameter.setEndTime(Date.from(LocalDateTime.now().plusYears(100).atZone(ZoneId.systemDefault()).toInstant()));
        }

    }

    @Override
    public String createSchedule(ScheduleParameter scheduleParameter) {
        setScheduleCronDefaultValue(scheduleParameter.getScheduleCronParameter());
        String url = MessageFormat.format("/projects/{0}/schedules", properties.getProjectCode());
        FormBody body = new FormBody.Builder()
                .add("processDefinitionCode", scheduleParameter.getFlowCode())
                .add("schedule", JSONUtils.toJSONString(scheduleParameter.getScheduleCronParameter()))
                .build();
        try {
            // create schedule instance
            String resultJson = post(url, body);
            DolphinResult<Schedule> result = JSONUtils.parseObject(resultJson, new TypeReference<DolphinResult<Schedule>>() {});
            checkResult(result, "createSchedule");
            int scheduleId = result.getData().getId();

            // Update schedule status to online
            updateScheduleToOnline(scheduleId);
            return String.valueOf(scheduleId);
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call createSchedule method exception", e);
        }
    }

    @Override
    public void updateSchedule(ScheduleParameter scheduleParameter) {
        String url = MessageFormat.format("/projects/{0}/schedules/update/{1}", properties.getProjectCode(), scheduleParameter.getFlowCode());
        FormBody body = new FormBody.Builder()
                .add("schedule", JSONUtils.toJSONString(scheduleParameter.getScheduleCronParameter()))
                .build();
        try {
            Integer scheduleId = Integer.valueOf(scheduleParameter.getScheduleCode());
            // Update schedule status to offline
            updateScheduleToOffline(scheduleId);

            // update schedule instance
            String resultJson = post(url, body);
            DolphinResult<Schedule> result = JSONUtils.parseObject(resultJson, new TypeReference<DolphinResult<Schedule>>() {});
            checkResult(result, "updateSchedule");

            // Update schedule status to online
            updateScheduleToOnline(scheduleId);
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call updateSchedule method exception", e);
        }
    }

    private void checkResult(DolphinResult result, String method) {
        if (Objects.isNull(result) || HTTP_SUCCESS != result.getCode()) {
            throw new SchedulerException(MessageFormat.format("dolphin scheduler call {0} method exception, {1}", method, Objects.isNull(result) ? "result is empty" : result.getMsg()));
        }
    }

    /**
     * update schedule state to online
     * @param scheduleId schedule id
     * @throws IOException
     */
    private void updateScheduleToOnline(int scheduleId) throws IOException {
        String url = MessageFormat.format("/projects/{0}/schedules/{1}/online", properties.getProjectCode(), scheduleId);
        FormBody body = new FormBody.Builder().build();
        String resultJson = post(url, body);
        DolphinResult result = JSONUtils.parseObject(resultJson, DolphinResult.class);
        checkResult(result, "updateScheduleToOnline");
    }

    /**
     * update schedule state to offline
     * @param scheduleId schedule id
     * @throws IOException
     */
    private void updateScheduleToOffline(int scheduleId) throws IOException {
        String url = MessageFormat.format("/projects/{0}/schedules/{1}/offline", properties.getProjectCode(), scheduleId);
        FormBody body = new FormBody.Builder().build();
        String resultJson = post(url, body);
        DolphinResult result = JSONUtils.parseObject(resultJson, DolphinResult.class);
        checkResult(result, "updateScheduleToOffline");
    }

    private void updateFlowState(long flowCode, String state) throws IOException {
        String url = MessageFormat.format("/projects/{0}/process-definition/{1}/release", properties.getProjectCode(), String.valueOf(flowCode));
        FormBody body = new FormBody.Builder()
                .add("releaseState", state)
                .build();
        String resultJson = post(url, body);
        DolphinResult result = JSONUtils.parseObject(resultJson, DolphinResult.class);
        checkResult(result, "updateFlowState");
    }

    // -----------------------------------------------------------------------------------------------------------------

    private String generateDolphinHttpTaskParameter(String taskParams) {
        HttpTaskParameter httpTaskParameter = JSONUtils.parseObject(taskParams, HttpTaskParameter.class);

        HttpParameter dolphinHttpParameter = new HttpParameter();
        dolphinHttpParameter.setUrl(httpTaskParameter.getUrl());
        dolphinHttpParameter.setHttpMethod(httpTaskParameter.getMethod());

        List<HttpProperty> dolphinHttpProperties = new ArrayList<>();
        // http header
        httpTaskParameter.getHeaders().forEach((key, value) -> {
            HttpProperty httpProperty = new HttpProperty();
            httpProperty.setHttpParametersType(HttpParametersType.HEADERS);
            httpProperty.setProp(key);
            httpProperty.setValue(value);
            dolphinHttpProperties.add(httpProperty);
        });

        // http parameter
        httpTaskParameter.getParams().forEach((key, value) -> {
            HttpProperty httpProperty = new HttpProperty();
            httpProperty.setHttpParametersType(HttpParametersType.PARAMETER);
            httpProperty.setProp(key);
            httpProperty.setValue(value);
            dolphinHttpProperties.add(httpProperty);
        });

        // http body
        Map<String, String> bodyMap = JSONUtils.parseObject(httpTaskParameter.getBody(), new TypeReference<Map<String, String>>() {});
        if (Objects.nonNull(bodyMap)) {
            bodyMap.forEach((key, value) -> {
                HttpProperty httpProperty = new HttpProperty();
                httpProperty.setHttpParametersType(HttpParametersType.BODY);
                httpProperty.setProp(key);
                httpProperty.setValue(value);
                dolphinHttpProperties.add(httpProperty);
            });
        }
        dolphinHttpParameter.setHttpParams(dolphinHttpProperties);
        dolphinHttpParameter.setConnectTimeout(httpTaskParameter.getConnectTimeout());
        dolphinHttpParameter.setSocketTimeout(httpTaskParameter.getSocketTimeout());
        return JSONUtils.toJSONString(dolphinHttpParameter);
    }

    @Override
    public String createSingleHttpTask(TaskParameter taskParameter) {

        String dolphinHttpParameterJson = generateDolphinHttpTaskParameter(taskParameter.getTaskParams());

        Long taskCode;
        try {
            taskCode = CodeGenerateUtils.getInstance().genCode();
        } catch (CodeGenerateUtils.CodeGenerateException e) {
            throw new SchedulerException("dolphin scheduler generate unique code exception", e);
        }

        // create task definition
        List<TaskDefinitionParameter> dolphinTaskDefinitionList = new ArrayList<>();
        TaskDefinitionParameter dolphinTaskDefinitionParameter = new TaskDefinitionParameter();
        dolphinTaskDefinitionParameter.setTaskParams(dolphinHttpParameterJson);
        dolphinTaskDefinitionParameter.setCode(taskCode);
        dolphinTaskDefinitionParameter.setTaskType("HTTP");
        dolphinTaskDefinitionParameter.setName(taskParameter.getName());
        dolphinTaskDefinitionParameter.setDescription(taskParameter.getDescription());
        dolphinTaskDefinitionList.add(dolphinTaskDefinitionParameter);

        // create task definition relations
        List<ProcessTaskRelationParameter> taskRelationList = new ArrayList<>();
        ProcessTaskRelationParameter processTaskRelationParameter = new ProcessTaskRelationParameter();
        processTaskRelationParameter.setPostTaskCode(taskCode);
        processTaskRelationParameter.setPostTaskVersion(1);
        taskRelationList.add(processTaskRelationParameter);

        // create flow dag locations
        List<ProcessDAGLocationParameter> locations = new ArrayList<>();
        ProcessDAGLocationParameter locationParameter = new ProcessDAGLocationParameter();
        locationParameter.setTaskCode(taskCode);
        locationParameter.setX(150);
        locationParameter.setY(150);
        locations.add(locationParameter);

        String url = MessageFormat.format("/projects/{0}/process-definition", properties.getProjectCode());
        FormBody body = new FormBody.Builder()
                .add("name", taskParameter.getName())
                .add("description", taskParameter.getDescription())
                .add("tenantCode", properties.getTenantCode())
                .add("taskRelationJson", JSONUtils.toJSONString(taskRelationList))
                .add("taskDefinitionJson", JSONUtils.toJSONString(dolphinTaskDefinitionList))
                .add("locations", JSONUtils.toJSONString(locations))
                .build();

        try {
            // create task instance and flow instance
            String resultJson = post(url, body);
            DolphinResult<ProcessDefinition> result = JSONUtils.parseObject(resultJson, new TypeReference<DolphinResult<ProcessDefinition>>() {});
            checkResult(result, "createSingleHttpTask");

            long flowCode = result.getData().getCode();
            // Update flow status to online
            updateFlowState(flowCode, ReleaseState.ONLINE.toString());
            return String.valueOf(flowCode);
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call createSingleHttpTask method exception", e);
        }
    }

    @Override
    public void deleteFlowByCode(String flowCode) {
        String url = MessageFormat.format("/projects/{0}/process-definition/{1}", properties.getProjectCode(), flowCode);
        try {
            // Update flow status to online
            updateFlowState(Long.valueOf(flowCode), ReleaseState.OFFLINE.toString());

            // delete flow by code
            String resultJson = delete(url);
            DolphinResult result = JSONUtils.parseObject(resultJson, DolphinResult.class);
            checkResult(result, "deleteFlowByCode");
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call deleteFlowByCode method exception", e);
        }
    }

    @Override
    public FlowDefinition queryFlowByCode(String flowCode) {
        String url = MessageFormat.format("/projects/{0}/process-definition/{1}", properties.getProjectCode(), flowCode);
        try {
            String resultJson = get(url);

            DolphinResult<ComplexProcessDefinition> result = JSONUtils.parseObject(
                resultJson, new TypeReference<DolphinResult<ComplexProcessDefinition>>() {});

            ComplexProcessDefinition complexProcessDefinition = result.getData();
            FlowDefinition flowDefinition = new FlowDefinition();
            BeanUtils.copyProperties(complexProcessDefinition.getProcessDefinition(), flowDefinition);

            return flowDefinition;
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call queryFlowByCode method exception", e);
        }
    }

    @Override
    public PageResponse<FlowInstance> queryFlowInstanceListPage(PageRequest pageRequest) {
        String url = MessageFormat.format("/projects/{0}/process-instances??searchVal={1}&pageSize={2}&pageNo={3}",
            properties.getProjectCode(),
            StringUtils.isNotBlank(pageRequest.getSearchVal()) ? pageRequest.getSearchVal() : "",
            pageRequest.getPageSize(),
            pageRequest.getPageNo());

        try {
            String resultJson = get(url);
            DolphinResult<DolphinPageInfo<ProcessInstance>> result = JSONUtils.parseObject(
                resultJson, new TypeReference<DolphinResult<DolphinPageInfo<ProcessInstance>>>() {});

            DolphinPageInfo<ProcessInstance> pageInfo = result.getData();

            PageResponse<FlowInstance> pageResponse = new PageResponse<>();

            pageResponse.setPageNo(pageInfo.getCurrentPage());
            pageResponse.setPageSize(pageInfo.getPageSize());
            pageResponse.setTotal(Integer.toUnsignedLong(pageInfo.getTotal()));
            List<FlowInstance> flowInstanceList = new ArrayList<>();
            pageResponse.setData(flowInstanceList);

            pageInfo.getTotalList().forEach(processInstance -> {
                FlowInstance flowInstance = new FlowInstance();
                BeanUtils.copyProperties(processInstance, flowInstance);
                flowInstance.setFlowCode(processInstance.getProcessDefinitionCode());
                flowInstanceList.add(flowInstance);
            });
            return pageResponse;
        } catch (IOException e) {
            throw new SchedulerException("dolphin scheduler call queryFlowInstanceListPage method exception", e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * get http request
     * @param url url
     * @return json response
     * @throws IOException
     */
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

    /**
     * Form post http request
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

    public String delete(String url) throws IOException {
        url = properties.getServiceUrl() + url;
        Request request = new Request.Builder()
                .url(url)
                .delete()
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

    public String put(String url, FormBody body) throws IOException {
        url = properties.getServiceUrl() + url;
        Request request = new Request.Builder()
                .url(url)
                .put(body)
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
