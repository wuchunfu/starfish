package org.metahut.starfish.ingestion.collector.hive.models;

import java.util.Collection;

public class HiveCluster {

    private String name;
    private String type;
    private String description;

    private Collection<HiveDB> dbs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<HiveDB> getDbs() {
        return dbs;
    }

    public void setDbs(Collection<HiveDB> dbs) {
        this.dbs = dbs;
    }
}
