package org.metahut.starfish.api.controller;

import org.metahut.starfish.api.dto.ResultEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("ingestion")
public interface IngestionController {

    @GetMapping("testConnection")
    ResultEntity testConnection(String type, String parameter);
}
