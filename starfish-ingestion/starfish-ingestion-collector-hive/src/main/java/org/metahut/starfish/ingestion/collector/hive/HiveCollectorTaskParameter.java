package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import org.apache.commons.lang3.StringUtils;

public class HiveCollectorTaskParameter extends AbstractCollectorParameter {

    private String clusterName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public boolean checkParameter() {
        return StringUtils.isNotBlank(clusterName);
    }
}
