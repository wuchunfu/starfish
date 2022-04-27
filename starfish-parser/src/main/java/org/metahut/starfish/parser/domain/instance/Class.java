package org.metahut.starfish.parser.domain.instance;

import org.metahut.starfish.parser.domain.SymbolConstants;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class Class extends TagLoader implements Serializable {

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

    public final String fullClassName() {
        if (packagePath == null || packagePath.length() == 0) {
            return name;
        }
        return packagePath + SymbolConstants.PACKAGE_SPLIT + name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
