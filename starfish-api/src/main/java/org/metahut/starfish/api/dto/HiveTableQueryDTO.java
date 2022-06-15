package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.metahut.starfish.api.Constants.HIVE_TABLE_TYPE_NAME;

/**
 *
 */
@ApiModel("hive table page query dto")
public class HiveTableQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "hive cluster id")
    private Long hiveClusterId;

    @ApiModelProperty(value = "hive cluster name")
    private String hiveClusterName;

    @ApiModelProperty(value = "hive db name")
    private String hiveDbName;

    @ApiModelProperty(value = "hive table name")
    private String hiveTableName;

    @ApiModelProperty(value = "hive create begin time")
    private Date createBeginTime;

    @ApiModelProperty(value = "hive create end time")
    private Date createEndTime;

    @ApiModelProperty(value = "hive update begin time")
    private Date updateBeginTime;

    @ApiModelProperty(value = "hive update end time")
    private Date updateEndTime;

    public Long getHiveClusterId() {
        return hiveClusterId;
    }

    public void setHiveClusterId(Long hiveClusterId) {
        this.hiveClusterId = hiveClusterId;
    }

    public String getHiveClusterName() {
        return hiveClusterName;
    }

    public void setHiveClusterName(String hiveClusterName) {
        this.hiveClusterName = hiveClusterName;
    }

    public String getHiveDbName() {
        return hiveDbName;
    }

    public void setHiveDbName(String hiveDbName) {
        this.hiveDbName = hiveDbName;
    }

    public String getHiveTableName() {
        return hiveTableName;
    }

    public void setHiveTableName(String hiveTableName) {
        this.hiveTableName = hiveTableName;
    }

    public Date getCreateBeginTime() {
        return createBeginTime;
    }

    public void setCreateBeginTime(Date createBeginTime) {
        this.createBeginTime = createBeginTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(Date updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public Date getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(Date updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    public AbstractQueryCondition<HiveTableResponseDTO> toCondition() {
        AbstractQueryCondition<HiveTableResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(HiveTableResponseDTO.class);
        condition.setFilters(Arrays.asList(collectorTaskPiece()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer eachPointer = new EachPointer(LinkCategory.RELATIONSHIP,RelationType.CHILD);
        map.put("db",eachPointer);
        map.put("columns",new EachPointer(LinkCategory.RELATIONSHIP,RelationType.CHILD));
        map.put("partitionKeys",new EachPointer(LinkCategory.RELATIONSHIP,RelationType.CHILD));
        EachPointer dbPointer = new EachPointer(LinkCategory.RELATIONSHIP,RelationType.CHILD);
        Map<String, EachPointer> map1 = new HashMap<>();
        map1.put("cluster",dbPointer);
        eachPointer.setEachPointers(map1);
        return map;
    }

    /**
     * ENTITY
     *      name
     * @return
     */
    private ConditionPiece collectorTaskPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        List<BinaryExpression> expressions = new ArrayList<>();
        expressions.addAll(Expression.entity());
        Map<String,List<ConditionPiece>> map = new HashMap<>();
        map.putAll(rel1());
        if (StringUtils.isNotEmpty(hiveTableName)) {
            map.putAll(rel0());
        }
        if (this.createBeginTime != null) {
            if (this.createEndTime != null) {
                expressions.add(Expression.dateBetweenAnd(Expression.CREATE_TIME,this.createBeginTime,this.createEndTime));
            } else {
                expressions.add(Expression.dateGreaterThanOrEqualTo(Expression.CREATE_TIME,this.createBeginTime));
            }
        } else {
            if (this.createEndTime != null) {
                expressions.add(Expression.dateLessThanOrEqualTo(Expression.CREATE_TIME,this.createEndTime));
            }
        }
        if (this.updateBeginTime != null) {
            if (this.updateEndTime != null) {
                expressions.add(Expression.dateBetweenAnd(Expression.UPDATE_TIME, this.updateBeginTime,this.updateEndTime));
            } else {
                expressions.add(Expression.dateGreaterThanOrEqualTo(Expression.UPDATE_TIME,this.updateBeginTime));
            }
        } else {
            if (this.updateEndTime != null) {
                expressions.add(Expression.dateLessThanOrEqualTo(Expression.UPDATE_TIME,this.updateEndTime));
            }
        }
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel0() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES,Arrays.asList(propertyNamePiece()));
        return result;
    }

    private ConditionPiece propertyNamePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike(Expression.NAME,this.hiveTableName))));
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel1() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        List<ConditionPiece> conditionPieces = new ArrayList<>();
        conditionPieces.add(typeEntityRelationPiece());
        if (!StringUtils.isAllEmpty(hiveDbName,hiveClusterName) || hiveClusterId != null) {
            conditionPieces.add(entityRel());
        }
        result.put(Expression.PARENT,conditionPieces);
        return result;
    }

    private ConditionPiece typeEntityRelationPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel2());
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel2() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY,Arrays.asList(collectorTaskTypePiece()));
        return result;
    }

    private ConditionPiece collectorTaskTypePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.type(HIVE_TABLE_TYPE_NAME));
        return conditionPiece;
    }

    private ConditionPiece entityRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"tables"));
        conditionPiece.setNextConditionChain(rel4());
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel4() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY,Arrays.asList(collectorAdapterPiece()));
        return result;
    }

    private ConditionPiece collectorAdapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(rel5());
        return conditionPiece;
    }

    private Map<String, List<ConditionPiece>> rel5() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        if (StringUtils.isNotEmpty(hiveDbName)) {
            result.put(Expression.PROPERTIES, Arrays.asList(propertyPiece()));
        }
        result.put(Expression.PARENT, Arrays.asList(hiveDbRelPiece()));
        return result;
    }

    private ConditionPiece propertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike(Expression.NAME,this.hiveDbName))));
        return conditionPiece;
    }

    private ConditionPiece hiveDbRelPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"dbs"));
        conditionPiece.setNextConditionChain(rel6());
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel6() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.START_NODE_ENTITY, Arrays.asList(hiveClusterPiece()));
        return result;
    }

    private ConditionPiece hiveClusterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        if (hiveClusterId != null) {
            conditionPiece.setExpressions(Arrays.asList(Expression.id(this.hiveClusterId)));
        }
        if (StringUtils.isNotEmpty(hiveClusterName)) {
            conditionPiece.setNextConditionChain(rel7());
        }
        return conditionPiece;
    }

    private Map<String,List<ConditionPiece>> rel7() {
        Map<String,List<ConditionPiece>> result = new HashMap<>();
        result.put(Expression.PROPERTIES,Arrays.asList(clusterPropertyPiece()));
        return result;
    }

    private ConditionPiece clusterPropertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike(Expression.NAME,this.hiveClusterName))));
        return conditionPiece;
    }
}
