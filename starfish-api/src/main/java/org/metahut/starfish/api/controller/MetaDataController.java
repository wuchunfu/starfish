package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.ResultEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 */
@RequestMapping("metaData")
public interface MetaDataController {

    /**
     * batch to install metaData of an type
     * @param metaDataDTO
     * @return
     */
    @PostMapping("batch")
    ResultEntity batchRequest(@RequestBody BatchMetaDataDTO metaDataDTO) throws Exception;

}
