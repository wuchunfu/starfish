package org.metahut.starfish.server.controller;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.service.AbstractMetaDataService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@SpringBootTest
public class MetaDataControllerTest {

    @Autowired
    private AbstractMetaDataService<String,Long,Object> metaDataService;

    @Test
    public void testSave() throws AbstractMetaParserException {
        String typeName = "HiveTable";
        Map<String,Object> properties = new HashMap<>();
        properties.put("name","dwd.user_info");
        metaDataService.create(typeName,0L,properties);
    }

}
