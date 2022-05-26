package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.Category;
import org.metahut.starfish.unit.enums.RelationType;

import java.util.Map;

/**
 *
 */
public class EachPointer {

    private Category category;
    private RelationType relationType;

    private Map<String,EachPointer> eachPointers;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public Map<String, EachPointer> getEachPointers() {
        return eachPointers;
    }

    public void setEachPointers(Map<String, EachPointer> eachPointers) {
        this.eachPointers = eachPointers;
    }
}
