package org.metahut.starfish.service;

import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.function.*;

/**
 *
 */
public class MetaDataServiceImpl extends AbstractMetaDataService<String,String,String> {


    @Override
    protected AbstractSqlGraphService<String, String, String> graphApi() {
        return null;
    }

    @Override
    protected AbstractEnvironmentService<String, String> environmentApi() {
        return null;
    }

    @Override
    protected AbstractClassService<String, String> classApi() {
        return null;
    }

    @Override
    protected AbstractClassInstanceBridgeService<String, String> classInstanceBridgeApi() {
        return null;
    }

    @Override
    public void move(String toEnv, String fromEnv, long... classIds) throws AbstractMetaParserException {

    }

    @Override
    public void move(String toEnv, String fromEnv, String... instanceIds) throws AbstractMetaParserException {

    }
}
