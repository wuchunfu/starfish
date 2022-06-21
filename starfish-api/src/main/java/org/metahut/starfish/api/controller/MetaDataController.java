/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.BatchMetaDataDTO;
import org.metahut.starfish.api.dto.BusinessTermResponseDTO;
import org.metahut.starfish.api.dto.BusinessTermSearchRequestDTO;
import org.metahut.starfish.api.dto.CreateOrUpdateBusinessTermRequestDTO;
import org.metahut.starfish.api.dto.MetaDataDetailResponseDTO;
import org.metahut.starfish.api.dto.MetaDataRequestDTO;
import org.metahut.starfish.api.dto.MetaDataResponseDTO;
import org.metahut.starfish.api.dto.MetaDataVersionResponseDTO;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Deprecated
@RequestMapping("metaData")
public interface MetaDataController {

    /**
     * batch to install metaData of an type
     *
     * @param metaDataDTO
     * @return
     */
    @PostMapping("batchInstance")
    @ApiOperation(value = "batchInstance", notes = "BATCH_INSTNCE_CREATE")
    ResultEntity batchInstances(@RequestBody BatchMetaDataDTO metaDataDTO) throws Exception;

    /**
     * batch to install metaData of an type
     * @param metaDataDTO
     * @return
     */
    @Deprecated
    @PostMapping("batchType")
    @ApiOperation(value = "batchType", notes = "BATCH_TYPE_CREATE")
    ResultEntity batchType(@RequestBody TypeRequestBatchCreateOrUpdateDTO metaDataDTO) throws Exception;

    @PostMapping("downloadTemplate")
    @ApiOperation(value = "downloadTemplate", notes = "DOWNLOAD_TEMPLATE_NOTES")
    ResultEntity downloadTemplate();

    @PostMapping("uploadTemplate")
    @ApiOperation(value = "uploadTemplate", notes = "UPLOAD_TEMPLATE_NOTES")
    ResultEntity uploadTemplate(MultipartFile[] multipartFiles);

    //business term
    @PostMapping("createBusinessTerm")
    @ApiOperation(value = "createBusinessTerm", notes = "CREATE_BUSINESS_TERM_NOTES")
    ResultEntity<BusinessTermResponseDTO> createBusinessTerm(@RequestBody CreateOrUpdateBusinessTermRequestDTO businessTermRequestDTO);

    @PutMapping("updateBusinessTerm/{code}")
    @ApiOperation(value = "updateBusinessTerm", notes = "UPDATE_BUSINESS_TERM_NOTES")
    ResultEntity<BusinessTermResponseDTO> updateBusinessTerm(@PathVariable(value = "code", required = true) Long businessCode
            , @RequestBody CreateOrUpdateBusinessTermRequestDTO businessTermRequestDTO);

    @DeleteMapping("deleteBusinessTerm/{code}")
    @ApiOperation(value = "deleteBusinessTerm", notes = "DELETE_BUSINESS_TERM_NOTES")
    ResultEntity deleteBusinessTerm(@PathVariable(value = "code", required = true) Long businessCode);

    @GetMapping("queryBusinessTerm")
    @ApiOperation(value = "queryBusinessTerm", notes = "QUERY_BUSINESS_TERM_NOTES")
    ResultEntity<List<BusinessTermResponseDTO>> queryBusinessTerm(@RequestBody BusinessTermSearchRequestDTO businessTermSearchRequestDTO);

    @GetMapping("queryBusinessTermPage")
    @ApiOperation(value = "queryBusinessTermPage", notes = "QUERY_BUSINESS_TERM_PAGE_NOTES")
    ResultEntity<List<BusinessTermResponseDTO>> queryBusinessTermPage(@RequestBody BusinessTermSearchRequestDTO businessTermSearchRequestDTO);

    //metaData
    @GetMapping("queryMetaDataPage")
    @ApiOperation(value = "queryMetaDataPage", notes = "QUERY_META_DATA_PAGE_NOTES")
    ResultEntity<List<MetaDataResponseDTO>> queryMetaDataPage(@RequestBody MetaDataRequestDTO businessTermSearchRequestDTO);

    @GetMapping("queryMetaData")
    @ApiOperation(value = "queryMetaData", notes = "QUERY_META_DATA_NOTES")
    ResultEntity<List<MetaDataResponseDTO>> queryMetaData(@RequestBody MetaDataRequestDTO businessTermSearchRequestDTO);

    @PutMapping("updateQueryMetaData/{code}")
    @ApiOperation(value = "updateQueryMetaData", notes = "UPDATE_META_DATA_NOTES")
    ResultEntity<MetaDataResponseDTO> updateQueryMetaData(@PathVariable(value = "code", required = true) Long code, @RequestBody MetaDataRequestDTO updateMetaDataRequestDTO);

    @GetMapping("queryVersion/{code}")
    @ApiOperation(value = "queryVersion", notes = "QUERY_VERSION_NOTES")
    ResultEntity<MetaDataVersionResponseDTO> queryMetaDataVersion(@PathVariable(value = "code", required = true) Long code);

    @GetMapping("queryMetaDataDetail/{code}")
    @ApiOperation(value = "queryMetaDataDetail", notes = "QUERY_META_DATA_DETAIL_NOTES")
    ResultEntity<List<MetaDataDetailResponseDTO>> queryMetaDataDetail(@PathVariable(value = "code", required = true) Long code);

}
