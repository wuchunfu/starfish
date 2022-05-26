package org.metahut.starfish.parser.domain.enums;

import org.metahut.starfish.unit.enums.LinkCategory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
class LinkCategoryTest {

    @Test
    void valueOf() {
        List<String> names = Arrays.stream(LinkCategory.values()).map(LinkCategory::name).collect(Collectors.toList());
        Assertions.assertTrue(names.contains("SOURCE_TYPE"));
        Assertions.assertFalse(names.contains("help"));
    }
}
