package org.metahut.starfish.parser.domain.struct;

import org.metahut.starfish.parser.domain.enums.SfTagLife;

import java.util.Map;

/**
 *
 */
public abstract class TagLoader {

    private Map<SfTagLife, TagModel> tagModelMap;

    public Map<SfTagLife, TagModel> getTagModelMap() {
        return tagModelMap;
    }

    public void setTagModelMap(Map<SfTagLife, TagModel> tagModelMap) {
        this.tagModelMap = tagModelMap;
    }
}
