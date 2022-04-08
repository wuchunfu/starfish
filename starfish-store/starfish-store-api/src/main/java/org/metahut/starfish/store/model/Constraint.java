package org.metahut.starfish.store.model;

import org.metahut.starfish.store.common.ConstraintType;

import java.util.List;

public class Constraint {

    private String name;

    private String category;

    private List<String> propertyNames;

    private ConstraintType type;
}
