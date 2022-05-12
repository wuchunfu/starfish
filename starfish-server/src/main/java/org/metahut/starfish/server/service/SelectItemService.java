package org.metahut.starfish.server.service;

import org.metahut.starfish.api.dto.SelectItemRequestDTO;
import org.metahut.starfish.api.dto.SelectItemResponseDTO;

import java.util.List;
import java.util.Map;

public interface SelectItemService {

    Map<String, List<SelectItemResponseDTO>> queryList(SelectItemRequestDTO selectItemRequestDTO);
}
