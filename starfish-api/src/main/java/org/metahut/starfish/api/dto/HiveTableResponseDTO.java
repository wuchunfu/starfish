package org.metahut.starfish.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel(description = "hive table response dto")
public class HiveTableResponseDTO {

    @ApiModelProperty(value = "hive table id")
    private Long id;

    @ApiModelProperty(value = "hive table name")
    private String name;

    @ApiModelProperty(value = "hive table description")
    private String description;

    @ApiModelProperty(value = "hive table owner")
    private String owner;

    @ApiModelProperty(value = "hive table last access time")
    private String lastAccessTime;

    @ApiModelProperty(value = "hive table partition keys")
    private List<String> partitionKeys;

    @ApiModelProperty(value = "hive table type")
    private String tableType;

    @ApiModelProperty(value = "hive table create time")
    private Date createTime;

    @ApiModelProperty(value = "hive table update time")
    private Date updateTime;

    @ApiModelProperty(value = "hive table db")
    private HiveDBResponseDTO db;

    @ApiModelProperty(value = "hive table columns")
    private List<HiveColumnResponseDTO> columns;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public List<String> getPartitionKeys() {
        return partitionKeys;
    }

    public void setPartitionKeys(List<String> partitionKeys) {
        this.partitionKeys = partitionKeys;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public HiveDBResponseDTO getDb() {
        return db;
    }

    public void setDb(HiveDBResponseDTO db) {
        this.db = db;
    }

    public void setColumns(List<HiveColumnResponseDTO> columns) {
        this.columns = columns;
    }

    public List<HiveColumnResponseDTO> getColumns() {
        return columns;
    }
}
