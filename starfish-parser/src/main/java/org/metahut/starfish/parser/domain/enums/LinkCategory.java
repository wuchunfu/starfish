package org.metahut.starfish.parser.domain.enums;

/**
 * the rel type between two node which owns different type category
 * It should be the only link type between any two nodes which owned different types
 */
public enum LinkCategory {
    SOURCE_TYPE,
    TYPE_ENTITY,
    SOURCE_ENTITY,
    RELATIONSHIP;
}
