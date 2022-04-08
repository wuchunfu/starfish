package org.metahut.starfish.parser.domain.instance;

import org.metahut.starfish.parser.domain.SymbolConstants;
import org.metahut.starfish.parser.domain.struct.TagLoader;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Class extends TagLoader {
    /**
     * Serial Version UID
     */
    private long serialVersionUID = ThreadLocalRandom.current().nextLong();

    /**
     * name
     */
    private String name;

    /**
     * package path
     */
    private String packagePath;

    /**
     * attribute model list
     */
    private List<Attribute> attributes;

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public final String getFullClassName() {
        return packagePath + SymbolConstants.PACKAGE_SPLIT + name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
