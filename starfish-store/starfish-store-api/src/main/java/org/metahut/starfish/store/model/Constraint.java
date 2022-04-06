package org.metahut.starfish.store.model;

import java.util.List;
import org.metahut.starfish.store.common.ConstraintType;

public class Constraint {

    private String name;

    private String category;

    private List<String> propertyNames;

    private ConstraintType type;
}
