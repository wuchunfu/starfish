package org.metahut.starfish.ingestion.collector.api;

public class TaskContext {

    private Long adapterId;
    private String adapterParameter;
    private String taskParameter;

    public Long getAdapterId() {
        return adapterId;
    }

    public void setAdapterId(Long adapterId) {
        this.adapterId = adapterId;
    }

    public String getAdapterParameter() {
        return adapterParameter;
    }

    public void setAdapterParameter(String adapterParameter) {
        this.adapterParameter = adapterParameter;
    }

    public String getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(String taskParameter) {
        this.taskParameter = taskParameter;
    }
}
