package org.metahut.starfish.parser.domain.instance;

import org.metahut.starfish.parser.domain.enums.TagLife;

import java.io.Serializable;
import java.util.Map;

/**
 *
 */
public abstract class TagLoader implements Serializable {

    private Map<TagLife, TagModel> tagModelMap;

    public Map<TagLife, TagModel> getTagModelMap() {
        return tagModelMap;
    }

    public void setTagModelMap(Map<TagLife, TagModel> tagModelMap) {
        this.tagModelMap = tagModelMap;
    }
}
