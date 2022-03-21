package org.metahut.starfish.parser.domain.struct;

import org.metahut.starfish.parser.domain.enums.TagLife;

import java.util.Map;

/**
 *
 */
public abstract class TagLoader {

    private Map<TagLife, TagModel> tagModelMap;

    public Map<TagLife, TagModel> getTagModelMap() {
        return tagModelMap;
    }

    public void setTagModelMap(Map<TagLife, TagModel> tagModelMap) {
        this.tagModelMap = tagModelMap;
    }
}
