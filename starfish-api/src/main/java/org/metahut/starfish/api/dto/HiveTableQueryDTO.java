package org.metahut.starfish.api.dto;

import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.enums.TypeCategory;
import org.metahut.starfish.unit.expression.BinaryExpression;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HiveTableQueryDTO extends PageRequestDTO {
    private String hiveClusterName;
    private String hiveDbName;
    private String hiveTableName;
    private Date createBeginTime;
    private Date createEndTime;
    private Date updateBeginTime;
    private Date updateEndTime;

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

    public AbstractQueryCondition<HiveTableResponseDTO> toQueryCondition() {
        AbstractQueryCondition<HiveTableResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(HiveTableResponseDTO.class);
        condition.setFilters(Arrays.asList(collectorTaskPiece()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }

    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        EachPointer eachPointer = new EachPointer();
        eachPointer.setCategory(TypeCategory.ENTITY);
        eachPointer.setRelationType(RelationType.PARENT);
        map.put("tables",eachPointer);
        EachPointer dbPointer = new EachPointer();
        dbPointer.setCategory(TypeCategory.ENTITY);
        dbPointer.setRelationType(RelationType.PARENT);
        Map<String, EachPointer> map1 = new HashMap<>();
        map1.put("dbs",dbPointer);
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
        Map<String,ConditionPiece> map = new HashMap<>();
        map.putAll(rel1());
        if (ObjectUtils.allNotNull(hiveClusterName,createBeginTime,createEndTime,updateBeginTime,updateEndTime)) {
            map.putAll(rel0());
        }
        if (StringUtils.isNotEmpty(hiveDbName) && StringUtils.isNotEmpty(hiveClusterName)) {
            map.putAll(rel3());
        }
        if (this.createBeginTime != null) {
            if (this.createEndTime != null) {
                expressions.addAll(Expression.keyValueDateBetweenAnd("createTime", this.createBeginTime,this.createEndTime));
            } else {
                expressions.addAll(Expression.keyValueDateGreaterThanOrEqualTo("createTime",this.createBeginTime));
            }
        } else {
            if (this.createEndTime != null) {
                expressions.addAll(Expression.keyValueDateLessThanOrEqualTo("createTime",this.createEndTime));
            }
        }
        if (this.updateBeginTime != null) {
            if (this.updateEndTime != null) {
                expressions.addAll(Expression.keyValueDateBetweenAnd("createTime", this.updateBeginTime,this.updateEndTime));
            } else {
                expressions.addAll(Expression.keyValueDateGreaterThanOrEqualTo("createTime",this.updateBeginTime));
            }
        } else {
            if (this.updateEndTime != null) {
                expressions.addAll(Expression.keyValueDateLessThanOrEqualTo("createTime",this.updateEndTime));
            }
        }
        conditionPiece.setExpressions(expressions);
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel0() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",propertyNamePiece());
        return result;
    }

    private ConditionPiece propertyNamePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("name",this.hiveTableName))));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel1() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("parent",typeEntityRelationPiece());
        return result;
    }

    private ConditionPiece typeEntityRelationPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.TYPE_ENTITY,LinkCategory.TYPE_ENTITY.name()));
        conditionPiece.setNextConditionChain(rel2());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel2() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("startNodeEntity",collectorTaskTypePiece());
        return result;
    }

    private ConditionPiece collectorTaskTypePiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Expression.entity("org.starfish.HiveTable"));
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel3() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("parent",entityRel());
        return result;
    }

    private ConditionPiece entityRel() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"tables"));
        conditionPiece.setNextConditionChain(rel4());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel4() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("endNodeEntity",collectorAdapterPiece());
        return result;
    }

    private ConditionPiece collectorAdapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(rel5());
        return conditionPiece;
    }

    private Map<String, ConditionPiece> rel5() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",propertyPiece());
        result.put("parent", hiveDbRelPiece());
        return result;
    }

    private ConditionPiece propertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("name",this.hiveDbName))));
        return conditionPiece;
    }

    private ConditionPiece hiveDbRelPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"cluster"));
        conditionPiece.setNextConditionChain(rel6());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel6() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("endNodeEntity",adapterPiece());
        return result;
    }

    private ConditionPiece adapterPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setNextConditionChain(rel7());
        return conditionPiece;
    }

    private Map<String,ConditionPiece> rel7() {
        Map<String,ConditionPiece> result = new HashMap<>();
        result.put("properties",clusterPropertyPiece());
        return result;
    }

    private ConditionPiece clusterPropertyPiece() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(Arrays.asList(Expression.and(Expression.keyValueLike("name",this.hiveClusterName))));
        return conditionPiece;
    }
}
