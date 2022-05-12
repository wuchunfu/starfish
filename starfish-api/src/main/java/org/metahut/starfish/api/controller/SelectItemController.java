package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.SelectItemRequestDTO;
import org.metahut.starfish.api.dto.SelectItemResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Api(tags = "SELECT_ITEM_TAG")
@RequestMapping("selectItem")
public interface SelectItemController {

    @ApiOperation(value = "queryList", notes = "QUERY_SELECT_ITEM_LIST_NOTES")
    @GetMapping("queryList")
    ResultEntity<Map<String, List<SelectItemResponseDTO>>> queryList(SelectItemRequestDTO selectItemRequestDTO);
}
