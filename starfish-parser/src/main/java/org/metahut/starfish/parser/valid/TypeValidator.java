package org.metahut.starfish.parser.valid;

import org.metahut.starfish.parser.domain.instance.Class;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
public class TypeValidator<K,T> {

    private static final AtomicLong CLASS_VERSION = new AtomicLong(0);

    private final List<Class> classes;

    private final Map<K,Object> instances;

    public TypeValidator(List<Class> classes, Map<K, Object> instances) {
        this.classes = classes;
        this.instances = instances;
    }

    public void add(K id,String property,T object) {

    }

    public void modify(K id,String property,T object) {

    }

    public void delete(K id,String property) {

    }

    public void valid() {
        //1.生成class
        //2.验证
    }
}
