package org.metahut.starfish.unit.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 *
 */
@JsonDeserialize(using = CategoryDeserializer.class)
public interface Category {

    String name();
}
