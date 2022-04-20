package org.metahut.starfish.parser.domain.enums;

/**
 * 主要是关系
 *  例如 entity 属于 type的实现 type 又归属 source
 * link？
 *      source{name,category,desc,} ->sourceName
 *          type
 *          entity
 *          relationship
 *  Source      :     name: Hive       category: Source
 *  Type        :     name: HiveTable  category: CLASSIFICATION
 *      type -[how rel or otherProperty]-> entity
 *
 *
 *      atlas 类型跟entity分开
 *  ******************** link or not ****************
 *  Entity      :     name: ?          category: HiveTable
 *  RELATIONSHIP:     name: ?          category: ?
 */
public enum TypeCategory {
    /**
     * the beginner of typeCategory
     */
    SOURCE,
    CLASSIFICATION,
    ENTITY,
    RELATIONSHIP,

    PRIMITIVE,
    OBJECT_ID_TYPE,
    ENUM,
    STRUCT,
    ARRAY,
    MAP,

    BUSINESS_METADATA;
}
