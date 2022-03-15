package org.metahut.starfish.parser.domain.enums;

/**
 * Type of tag
 * TODO
 *      countConstraint;
 *      uniqueConstraint;
 *      typeConvertConstraint (强校验，补正java的泛型的不足？ 不对 继承的回原）
 */
public enum SfTagCategory {
    SEARCH,
    DESCRIPTION,
    INDEX,
    CONSTRAINT,
    COLLECTION;
}
