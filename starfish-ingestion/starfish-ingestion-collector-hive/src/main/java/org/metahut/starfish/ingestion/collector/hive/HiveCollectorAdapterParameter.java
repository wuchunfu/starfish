package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import org.apache.commons.lang3.StringUtils;

public class HiveCollectorAdapterParameter extends AbstractCollectorParameter {

    private String hiveMetastoreUris;

    public String getHiveMetastoreUris() {
        return hiveMetastoreUris;
    }

    public void setHiveMetastoreUris(String hiveMetastoreUris) {
        this.hiveMetastoreUris = hiveMetastoreUris;
    }

    @Override
    public boolean checkParameter() {
        return StringUtils.isNotBlank(hiveMetastoreUris);
    }
}
