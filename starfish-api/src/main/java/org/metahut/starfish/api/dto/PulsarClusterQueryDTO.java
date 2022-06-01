package org.metahut.starfish.api.dto;

import org.metahut.starfish.api.Constants;
import org.metahut.starfish.unit.AbstractQueryCondition;
import org.metahut.starfish.unit.enums.TableType;
import org.metahut.starfish.unit.expression.ConditionPiece;
import org.metahut.starfish.unit.expression.Expression;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
@ApiModel(description = "pulsar cluster query dto")
public class PulsarClusterQueryDTO extends PageRequestDTO {

    @ApiModelProperty(value = "pulsar cluster name")
    private String name;

    @ApiModelProperty(value = "pulsar cluster service url")
    private String serviceUrl;

    @ApiModelProperty(value = "pulsar cluster service url tls")
    private String serviceUrlTls;

    @ApiModelProperty(value = "pulsar cluster broker service url")
    private String brokerServiceUrl;

    @ApiModelProperty(value = "pulsar cluster broker service url tls")
    private String brokerServiceUrlTls;

    @ApiModelProperty(value = "pulsar cluster proxy service url")
    private String proxyServiceUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceUrlTls() {
        return serviceUrlTls;
    }

    public void setServiceUrlTls(String serviceUrlTls) {
        this.serviceUrlTls = serviceUrlTls;
    }

    public String getBrokerServiceUrl() {
        return brokerServiceUrl;
    }

    public void setBrokerServiceUrl(String brokerServiceUrl) {
        this.brokerServiceUrl = brokerServiceUrl;
    }

    public String getBrokerServiceUrlTls() {
        return brokerServiceUrlTls;
    }

    public void setBrokerServiceUrlTls(String brokerServiceUrlTls) {
        this.brokerServiceUrlTls = brokerServiceUrlTls;
    }

    public String getProxyServiceUrl() {
        return proxyServiceUrl;
    }

    public void setProxyServiceUrl(String proxyServiceUrl) {
        this.proxyServiceUrl = proxyServiceUrl;
    }

    public AbstractQueryCondition<PulsarClusterResponseDTO> toCondition() {
        AbstractQueryCondition<PulsarClusterResponseDTO> condition = new AbstractQueryCondition<>();
        condition.setResultType(PulsarClusterResponseDTO.class);
        condition.setFilters(Arrays.asList(typePiece()));
        return condition;
    }

    private ConditionPiece typePiece() {
        ConditionPiece conditionPiece = ConditionPiece.entityWithType(Constants.HIVE_CLUSTER_TYPE_NAME);
        if (!StringUtils.isAllEmpty(this.name,this.serviceUrl,this.serviceUrlTls,this.brokerServiceUrl,this.brokerServiceUrlTls,this.proxyServiceUrl)) {
            conditionPiece.getNextConditionChain().put(Expression.PROPERTIES, Arrays.asList(propertyCondition()));
        }
        return conditionPiece;
    }

    private ConditionPiece propertyCondition() {
        ConditionPiece conditionPiece = new ConditionPiece();
        conditionPiece.setTableType(TableType.ENTITY_PROPERTY);
        conditionPiece.setExpressions(new ArrayList<>());
        if (StringUtils.isNotEmpty(this.name)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("name",this.name)));
        }
        if (StringUtils.isNotEmpty(this.serviceUrl)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("serviceUrl",this.serviceUrl)));
        }
        if (StringUtils.isNotEmpty(this.serviceUrlTls)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("serviceUrlTls",this.serviceUrlTls)));
        }
        if (StringUtils.isNotEmpty(this.brokerServiceUrl)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("brokerServiceUrl",this.brokerServiceUrl)));
        }
        if (StringUtils.isNotEmpty(this.brokerServiceUrlTls)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("brokerServiceUrlTls",this.brokerServiceUrlTls)));
        }
        if (StringUtils.isNotEmpty(this.proxyServiceUrl)) {
            conditionPiece.getExpressions().add(Expression.and(Expression.keyValueLike("proxyServiceUrl",this.proxyServiceUrl)));
        }
        return propertyCondition();
    }
}
