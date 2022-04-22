package org.metahut.starfish.parser.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SetAddTest {

    @Test
    public void setAddTest() {
        Set<String> set = new HashSet<>();
        Assertions.assertTrue(set.add("aaa"));
        Assertions.assertFalse(set.add("aaa"));
    }
}
