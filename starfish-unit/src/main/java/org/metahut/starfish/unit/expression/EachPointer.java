package org.metahut.starfish.unit.expression;

import org.metahut.starfish.unit.enums.Category;
import org.metahut.starfish.unit.enums.RelationType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EachPointer {

    private String reName;

    private Category category;

    private RelationType relationType;

    private Map<String,EachPointer> eachPointers = new HashMap<>();

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

    public EachPointer(Category category, RelationType relationType,String reName) {
        this.reName = reName;
        this.category = category;
        this.relationType = relationType;
    }

    public EachPointer() {
    }

    public EachPointer(Category category, RelationType relationType) {
        this.category = category;
        this.relationType = relationType;
    }

    public void addPointerChain(String property,EachPointer pointer) {
        this.eachPointers.put(property,pointer);
    }

    public String getReName() {
        return reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }
}
