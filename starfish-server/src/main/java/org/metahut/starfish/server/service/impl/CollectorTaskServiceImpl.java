package org.metahut.starfish.server.service.impl;

import org.metahut.starfish.api.dto.CollectorAdapterResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskCreateOrUpdateRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceLogResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskInstanceResponseDTO;
import org.metahut.starfish.api.dto.CollectorTaskRequestDTO;
import org.metahut.starfish.api.dto.CollectorTaskResponseDTO;
import org.metahut.starfish.api.dto.PageResponseDTO;
import org.metahut.starfish.scheduler.api.ExecutionStatus;
import org.metahut.starfish.scheduler.api.IScheduler;
import org.metahut.starfish.scheduler.api.PageResponse;
import org.metahut.starfish.scheduler.api.SchedulerException;
import org.metahut.starfish.scheduler.api.entity.TaskInstance;
import org.metahut.starfish.scheduler.api.parameters.HttpTaskParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleCronParameter;
import org.metahut.starfish.scheduler.api.parameters.ScheduleParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskInstanceLogRequestParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskInstanceRequestParameter;
import org.metahut.starfish.scheduler.api.parameters.TaskParameter;
import org.metahut.starfish.scheduler.dolphinscheduler.JSONUtils;
import org.metahut.starfish.server.config.IngestionConfiguration;
import org.metahut.starfish.server.service.CollectorTaskService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.service.AbstractMetaDataService;
import org.metahut.starfish.unit.EntityNameGentrator;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.metahut.starfish.api.Constants.COLLECTOR_TASK_TYPE_NAME;
import static org.metahut.starfish.api.Constants.PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION;
import static org.metahut.starfish.api.enums.Status.COLLECTOR_TASK_CREATE_SCHEDULE_FAIL;

@Service
@Transactional
public class CollectorTaskServiceImpl implements CollectorTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CollectorTaskServiceImpl.class);
    private final IScheduler scheduler;
    private final IngestionConfiguration ingestionConfiguration;
    private final ConversionService conversionService;

    private final AbstractMetaDataService<Long, Object> metaDataService;

    public CollectorTaskServiceImpl(IScheduler scheduler,
        IngestionConfiguration ingestionConfiguration,
        ConversionService conversionService,
        AbstractMetaDataService<Long, Object> metaDataService) {
        this.scheduler = scheduler;
        this.ingestionConfiguration = ingestionConfiguration;
        this.conversionService = conversionService;
        this.metaDataService = metaDataService;
    }

    //collector_name, description, datasourceId, collector_params, crontab, scheduler_flow_code，scheduler_cron_code???, state

    @Override
    public CollectorTaskResponseDTO create(CollectorTaskCreateOrUpdateRequestDTO requestDTO) {

        String qualifiedName = EntityNameGentrator.generateName(COLLECTOR_TASK_TYPE_NAME, requestDTO.getName());
        // create collector instance
        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        Long entityId = metaDataService.createEntityByTypeName(COLLECTOR_TASK_TYPE_NAME, qualifiedName, convert);

        String schedulerFlowCode = requestDTO.getSchedulerFlowCode();
        if (StringUtils.isBlank(schedulerFlowCode)) {
            try {
                // create single http task
                TaskParameter taskParameter = new TaskParameter();
                taskParameter.setTaskType("HTTP");
                taskParameter.setName(requestDTO.getName());
                taskParameter.setDescription(requestDTO.getDescription());

                HttpTaskParameter httpTaskParameter = new HttpTaskParameter();
                httpTaskParameter.setMethod("POST");

                // TODO How to deal with the generated scheduler task instance data when the url is changed?
                httpTaskParameter.setUrl(ingestionConfiguration.getCollectorExecuteRest());

                // TODO use java bean
                Map<String, Object> bodyMap = new HashMap<>();
                bodyMap.put("id", entityId);
                httpTaskParameter.setBody(JSONUtils.toJSONString(bodyMap));

                taskParameter.setTaskParams(JSONUtils.toJSONString(httpTaskParameter));

                // create a schedule flow
                schedulerFlowCode = scheduler.createSingleHttpTask(taskParameter);

                // configure a timing expression
                ScheduleParameter scheduleParameter = new ScheduleParameter();
                scheduleParameter.setFlowCode(schedulerFlowCode);
                ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
                scheduleCronParameter.setCrontab(requestDTO.getCron());
                scheduleParameter.setScheduleCronParameter(scheduleCronParameter);

                scheduler.createSchedule(scheduleParameter);
            } catch (SchedulerException e) {
                metaDataService.deleteEntity(entityId);
                Assert.throwException(e, COLLECTOR_TASK_CREATE_SCHEDULE_FAIL, requestDTO.getName(), e.getMessage());
            }
        }

        //TODO The update method needs to be optimized to support partial update
        convert.put("schedulerFlowCode", schedulerFlowCode);

        // update scheduler flow code in the collector task instance
        metaDataService.updateEntity(entityId, qualifiedName, convert);
        metaDataService.link(entityId, requestDTO.getAdapterId(), PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION);
        CollectorTaskResponseDTO collectorTaskResponseDTO = new CollectorTaskResponseDTO();
        collectorTaskResponseDTO.setId(entityId);
        return collectorTaskResponseDTO;
    }

    @Override
    public CollectorTaskResponseDTO update(Long id, CollectorTaskCreateOrUpdateRequestDTO requestDTO) {
        // query collector instance by code
        CollectorTaskResponseDTO instance = metaDataService.instance(id, CollectorTaskResponseDTO.class);
        if (instance.getAdapter() != null && instance.getAdapter().getId() != requestDTO.getAdapterId()) {
            metaDataService.crack(id, instance.getAdapter().getId(), PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION);
        }
        CollectorAdapterResponseDTO adapter = metaDataService.instance(requestDTO.getAdapterId(), CollectorAdapterResponseDTO.class);
        if (adapter != null) {
            metaDataService.link(id, requestDTO.getAdapterId(), PROPERTY_COLLECTOR_TASK_ADAPTER_RELATION);
        }
        // determine if the schedule cron instance needs to be updated
        if (!instance.getCron().equals(requestDTO.getCron())) {
            ScheduleParameter scheduleParameter = new ScheduleParameter();
            scheduleParameter.setFlowCode(instance.getSchedulerFlowCode());
            ScheduleCronParameter scheduleCronParameter = new ScheduleCronParameter();
            scheduleCronParameter.setCrontab(requestDTO.getCron());
            scheduleParameter.setScheduleCronParameter(scheduleCronParameter);
            scheduler.updateSchedule(scheduleParameter);
        }

        String qualifiedName = EntityNameGentrator.generateName(COLLECTOR_TASK_TYPE_NAME, requestDTO.getName());

        Map<String, Object> convert = conversionService.convert(requestDTO, Map.class);
        // update collector instance
        metaDataService.updateEntity(id, qualifiedName, convert);

        CollectorTaskResponseDTO collectorTaskResponseDTO = new CollectorTaskResponseDTO();
        collectorTaskResponseDTO.setId(id);
        return collectorTaskResponseDTO;
    }

    @Override
    public void deleteById(Long id) {
        CollectorTaskResponseDTO instance = metaDataService.instance(id, CollectorTaskResponseDTO.class);
        // delete schedule flow instance
        scheduler.deleteFlowByCode(instance.getSchedulerFlowCode());

        // delete collector instance
        metaDataService.deleteEntity(id);
    }

    @Override
    public CollectorTaskResponseDTO queryById(Long id) {
        return metaDataService.instance(id, CollectorTaskResponseDTO.class);
    }

    @Override
    public PageResponseDTO<CollectorTaskResponseDTO> queryListPage(CollectorTaskRequestDTO requestDTO) {
        Pageable pageable = PageRequest.of(requestDTO.getPageNo() - 1, requestDTO.getPageSize());
        Page<CollectorTaskResponseDTO> page = metaDataService.instances(requestDTO.toQueryCondition(), pageable);
        return PageResponseDTO.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }

    @Override
    public Collection<CollectorTaskResponseDTO> queryList(CollectorTaskRequestDTO requestDTO) {
        return metaDataService.instances(requestDTO.toQueryCondition());
    }

    @Override
    public PageResponseDTO<CollectorTaskInstanceResponseDTO> queryInstanceListPage(CollectorTaskInstanceRequestDTO requestDTO) {
        TaskInstanceRequestParameter pageRequest = new TaskInstanceRequestParameter();
        pageRequest.setPageNo(requestDTO.getPageNo());
        pageRequest.setPageSize(requestDTO.getPageSize());
        pageRequest.setBeginTime(requestDTO.getBeginTime());
        pageRequest.setEndTime(requestDTO.getEndTime());

        String adapterName = "";
        if (Objects.nonNull(requestDTO.getAdapterId())) {
            CollectorAdapterResponseDTO adapter = metaDataService.instance(requestDTO.getAdapterId(), CollectorAdapterResponseDTO.class);
            if (Objects.nonNull(adapter)) {
                adapterName = adapter.getName();
            }
        }
        String searchWord = "";
        if (StringUtils.isNotEmpty(requestDTO.getName())) {
            searchWord = requestDTO.getName();
        } else {
            searchWord = Joiner.on("-").skipNulls().join(
                Stream.of(requestDTO.getType(), adapterName).collect(
                Collectors.toList()));
        }
        if (StringUtils.isNotBlank(searchWord)) {
            pageRequest.setName(searchWord);
        }

        if (StringUtils.isNotBlank(requestDTO.getExecutionStatus())) {
            pageRequest.setExecutionStatus(ExecutionStatus.valueOf(requestDTO.getExecutionStatus()));
        }

        Collection<CollectorTaskResponseDTO> collection = this.queryList(new CollectorTaskRequestDTO());

        Map<String, List<CollectorTaskResponseDTO>> taskNameMaps = collection
            .stream()
            .filter(item -> StringUtils.isNotBlank(item.getName()))
            .collect(Collectors.groupingBy(item -> item.getName()));

        PageResponse<TaskInstance> pageResponse = scheduler.queryTaskInstanceListPage(pageRequest);

        PageResponseDTO result = new PageResponseDTO();

        result.setPageNo(pageResponse.getPageNo());
        result.setPageSize(pageResponse.getPageSize());
        result.setTotal(pageResponse.getTotal());

        List<CollectorTaskInstanceResponseDTO> list = new ArrayList<>();

        pageResponse.getData().forEach(instance -> {
            CollectorTaskInstanceResponseDTO taskInstanceResponseDTO = new CollectorTaskInstanceResponseDTO();
            taskInstanceResponseDTO.setId(Integer.toUnsignedLong(instance.getId()));
            taskInstanceResponseDTO.setCollectorTaskName(instance.getName());
            taskInstanceResponseDTO.setExecutionStatus(instance.getState());
            taskInstanceResponseDTO.setBeginTime(instance.getStartTime());
            taskInstanceResponseDTO.setEndTime(instance.getEndTime());
            taskInstanceResponseDTO.setCollectorTaskDesc(instance.getName());

            if (Objects.nonNull(taskNameMaps) && Objects.nonNull(taskNameMaps.get(instance.getName())) && taskNameMaps.get(instance.getName()).size() > 0) {
                CollectorTaskResponseDTO taskResponseDTO = taskNameMaps.get(instance.getName()).get(0);
                if (Objects.nonNull(taskResponseDTO.getAdapter())) {
                    Long adapterId = taskResponseDTO.getAdapter().getId();
                    CollectorAdapterResponseDTO adapter = metaDataService.instance(adapterId, CollectorAdapterResponseDTO.class);
                    taskInstanceResponseDTO.setCollectorAdapterName(adapter.getName());
                    taskInstanceResponseDTO.setCollectorAdapterType(adapter.getType());
                }
                taskInstanceResponseDTO.setCollectorTaskDesc(taskResponseDTO.getDescription());

                taskInstanceResponseDTO.setFlowCode(Long.parseLong(taskResponseDTO.getSchedulerFlowCode()));
            }
            list.add(taskInstanceResponseDTO);
        });
        result.setData(list);
        return result;
    }

    @Override
    public CollectorTaskInstanceLogResponseDTO queryInstanceLog(Integer instanceId, Integer offset, Integer limit) {
        TaskInstanceLogRequestParameter parameter = new TaskInstanceLogRequestParameter();
        if (Objects.isNull(offset) || offset < 0) {
            offset = 0;
        }

        if (Objects.isNull(limit) || limit < 0) {
            limit = 1000;
        }

        parameter.setOffset(offset);
        parameter.setLimit(limit);
        parameter.setTaskInstanceId(instanceId);

        String content = scheduler.queryFlowInstanceLog(parameter);

        CollectorTaskInstanceLogResponseDTO responseDTO = new CollectorTaskInstanceLogResponseDTO();
        responseDTO.setTaskInstanceId(instanceId);
        responseDTO.setContent(content);

        return responseDTO;
    }
}
