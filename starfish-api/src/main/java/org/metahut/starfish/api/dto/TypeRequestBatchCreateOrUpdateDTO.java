package org.metahut.starfish.api.dto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 */
public class TypeRequestBatchCreateOrUpdateDTO {

    private SourceBodyDTO source;

    private List<ClassDTO> types;

    public SourceBodyDTO getSource() {
        return source;
    }

    public void setSource(SourceBodyDTO source) {
        this.source = source;
    }

    public List<ClassDTO> getTypes() {
        return types;
    }

    public void setTypes(List<ClassDTO> types) {
        this.types = types;
    }

    public static class SourceBodyDTO {

        private String name;

        private Map<String,Object> attributes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, Object> attributes) {
            this.attributes = attributes;
        }
    }

    public static class ClassDTO {
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
        private List<AttributeDTO> attributes;

        private String nameAttributeRel;

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

        public List<AttributeDTO> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<AttributeDTO> attributes) {
            this.attributes = attributes;
        }

        public String getNameAttributeRel() {
            return nameAttributeRel;
        }

        public void setNameAttributeRel(String nameAttributeRel) {
            this.nameAttributeRel = nameAttributeRel;
        }
    }

    public static class AttributeDTO {

        /**
         * Attribute Name
         */
        private String name;

        /**
         * class type name
         */
        private String className;

        /**
         * class type belongs to
         */
        private String relType;

        /**
         * Attribute Is Array Or Not
         */
        private boolean array;

        //private Boolean isProxy        = Boolean.FALSE;
        //private Boolean isIncomplete   = Boolean.FALSE;
        //private Integer provenanceType = 0;


        /**
         * Attribute Default Value
         * TODO provider 序列化等问题
         * TODO tag 解决 ?
         */
        private String defaultValue;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getRelType() {
            return relType;
        }

        public void setRelType(String sfRelType) {
            this.relType = sfRelType;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public boolean isArray() {
            return array;
        }

        public void setArray(boolean array) {
            this.array = array;
        }
    }

}
