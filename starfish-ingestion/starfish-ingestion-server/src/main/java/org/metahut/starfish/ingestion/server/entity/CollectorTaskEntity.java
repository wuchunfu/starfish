package org.metahut.starfish.ingestion.server.entity;

public class CollectorTaskEntity {

    private Long id;

    private String name;

    private String description;

    private CollectorAdapterEntity adapter;

    private String parameter;

    private String cron;

    private String schedulerFlowCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollectorAdapterEntity getAdapter() {
        return adapter;
    }

    public void setAdapter(CollectorAdapterEntity adapter) {
        this.adapter = adapter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getSchedulerFlowCode() {
        return schedulerFlowCode;
    }

    public void setSchedulerFlowCode(String schedulerFlowCode) {
        this.schedulerFlowCode = schedulerFlowCode;
    }
}
