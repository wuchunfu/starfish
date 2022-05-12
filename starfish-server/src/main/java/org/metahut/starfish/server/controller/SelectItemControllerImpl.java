package org.metahut.starfish.server.controller;

import org.metahut.starfish.api.controller.SelectItemController;
import org.metahut.starfish.api.dto.ResultEntity;
import org.metahut.starfish.api.dto.SelectItemRequestDTO;
import org.metahut.starfish.api.dto.SelectItemResponseDTO;
import org.metahut.starfish.server.service.SelectItemService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SelectItemControllerImpl implements SelectItemController {

    private final SelectItemService selectItemService;

    public SelectItemControllerImpl(SelectItemService selectItemService) {
        this.selectItemService = selectItemService;
    }

    @Override
    public ResultEntity<Map<String, List<SelectItemResponseDTO>>> queryList(SelectItemRequestDTO selectItemRequestDTO) {
        return ResultEntity.success(selectItemService.queryList(selectItemRequestDTO));
    }
}
