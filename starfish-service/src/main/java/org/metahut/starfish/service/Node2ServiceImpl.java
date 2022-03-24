package org.metahut.starfish.service;

import org.metahut.starfish.parser.function.AbstractInstanceService;
import org.metahut.starfish.parser.function.AbstractNodeService;
import org.metahut.starfish.parser.function.AbstractPropertyService;

import javax.annotation.Resource;

/**
 *
 */
public class Node2ServiceImpl<E,K,T>  extends AbstractNodeService<E,K,T> {
    @Resource
    private AbstractInstanceService<E, K, T> instanceService;
    @Resource
    private AbstractPropertyService<E,K, T> propertyService;


    @Override
    protected AbstractInstanceService<E, K, T> getInstanceService() {
        return null;
    }

    @Override
    protected AbstractPropertyService<E,K, T> getPropertyService() {
        return null;
    }


}
