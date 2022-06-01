package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.Constants;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.expression.ConditionPiece;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 *
 */
@ApiModel(description = "pulsar namespace query dto")
public class PulsarNamespaceQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "pulsar namespace id")
    private Long id;

    @ApiModelProperty(value = "pulsar namespace name")
    private String name;

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

    public AbstractQueryCondition<PulsarNamespaceResponseDTO> toCondition() {
        AbstractQueryCondition<PulsarNamespaceResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(PulsarNamespaceResponseDTO.class);
        condition.setFilters(Arrays.asList(typePiece()));
        return condition;
    }

    private ConditionPiece typePiece() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithType(Constants.HIVE_CLUSTER_TYPE_NAME);
        return conditionPiece;
    }

}
