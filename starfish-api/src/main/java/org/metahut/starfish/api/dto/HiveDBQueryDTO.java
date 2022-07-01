package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.Constants;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.LinkCategory;
import org.metahut.starfish.unit.enums.RelationType;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.EachPointer;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class HiveDBQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "hive db id")
    private Long id;

    @ApiModelProperty(value = "hive db name")
    private String name;

    @ApiModelProperty(value = "hive db description")
    private String description;

    @ApiModelProperty(value = "hive db owner")
    private String owner;

    @ApiModelProperty(value = "hive db location")
    private String location;

    @ApiModelProperty(value = "hive db parameters")
    private String parameters;

    @ApiModelProperty(value = "hive cluster id")
    private Long clusterId;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public AbstractQueryCondition<HiveDBResponseDTO> toCondition() {
        AbstractQueryCondition<HiveDBResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(HiveDBResponseDTO.class);
        condition.setFilters(Arrays.asList(typePiece()));
        condition.setEachPointers(eachPointerMap());
        return condition;
    }
    
    private Map<String, EachPointer> eachPointerMap() {
        Map<String, EachPointer> map = new HashMap<>();
        map.put("dbs",new EachPointer(LinkCategory.RELATIONSHIP, RelationType.PARENT,"cluster"));
        return map;
    }

    private ConditionPiece typePiece() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithTypeAndIdAndQualifiedName(Constants.HIVE_DB_TYPE_NAME,id,null);
        if (!StringUtils.isAllEmpty(this.name,this.description,this.owner,this.location,this.parameters)) {
            conditionPiece.getNextConditionChain().put(Expression.PROPERTIES, Arrays.asList(propertyCondition()));
        }
        if (clusterId != null) {
            conditionPiece.getNextConditionChain().put(Expression.CHILDREN,Arrays.asList(clusterRelation()));
        }
        return conditionPiece;
    }

    private ConditionPiece clusterRelation() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.RELATION);
        conditionPiece.setExpressions(Expression.rel(LinkCategory.RELATIONSHIP,"cluster"));
        Map<String, List<ConditionPiece>> map = new HashMap<>();
        map.put(Expression.END_NODE_ENTITY,Arrays.asList(clusterIdCondition()));
        conditionPiece.setNextConditionChain(map);
        return conditionPiece;
    }

    private ConditionPiece clusterIdCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY);
        conditionPiece.setExpressions(Arrays.asList(Expression.id(clusterId)));
        return conditionPiece;
    }

    private ConditionPiece propertyCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList<>());
        if (StringUtils.isNotEmpty(this.name)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("name",this.name)));
        }
        if (StringUtils.isNotEmpty(this.description)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("description",this.description)));
        }
        if (StringUtils.isNotEmpty(this.owner)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("owner",this.owner)));
        }
        if (StringUtils.isNotEmpty(this.location)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("location",this.location)));
        }
        if (StringUtils.isNotEmpty(this.parameters)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("parameters",this.parameters)));
        }
        return conditionPiece;
    }
}
