package org.metahut.starfish.unit;

/**
 *
 */
public class TypeNameQueryConditionWithPage extends TypeNameQueryCondition {
    private Integer pageSize;
    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

}
