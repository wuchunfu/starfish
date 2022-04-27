package org.metahut.starfish.store.rdbms.common;

import java.util.Map;
import java.util.Map.Entry;

/**
 * author Hua Jiang
 */

public class PropertyValue<K, V> implements Entry<K, V>, java.io.Serializable {
    private K key;
    private V value;

    public PropertyValue() {}

    public PropertyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return eq(key, e.getKey()) && eq(value, e.getValue());
    }

    @Override
    public int hashCode() {
        return (key   == null ? 0 :   key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
