package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.function.AbstractMetaDataService;
import org.metahut.starfish.parser.function.AbstractSqlGraphService;
import org.metahut.starfish.parser.function.AbstractTypeInstanceBridgeService;
import org.metahut.starfish.parser.function.AbstractTypeService;

/**
 *
 */
public class MetaDataServiceImpl extends AbstractMetaDataService<String,String,String> {

    @Override
    protected AbstractSqlGraphService<String, String, String> graphApi() {
        return null;
    }

    @Override
    protected AbstractTypeService<String, String> classApi() {
        return null;
    }

    @Override
    protected AbstractTypeInstanceBridgeService<String, String> classInstanceBridgeApi() {
        return null;
    }

    @Override
    public void move(String toEnv, String fromEnv, long... classIds) throws AbstractMetaParserException {

    }

    @Override
    public void move(String toEnv, String fromEnv, String... instanceIds) throws AbstractMetaParserException {

    }
}
