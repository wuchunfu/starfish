package org.metahut.starfish.scheduler.api;

import lombok.Data;

@Data
public class PageRequest {

    private String searchVal;
    private Integer pageNo;
    private Integer pageSize;

}
