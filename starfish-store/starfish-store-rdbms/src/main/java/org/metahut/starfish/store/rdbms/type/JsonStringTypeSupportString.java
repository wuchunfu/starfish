//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.metahut.starfish.store.rdbms.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.AbstractHibernateType;
import com.vladmihalcea.hibernate.type.json.internal.JsonStringSqlTypeDescriptor;
import com.vladmihalcea.hibernate.type.util.Configuration;
import com.vladmihalcea.hibernate.type.util.ObjectMapperWrapper;
import org.hibernate.usertype.DynamicParameterizedType;

import java.lang.reflect.Type;
import java.util.Properties;


public class JsonStringTypeSupportString extends AbstractHibernateType<Object> implements DynamicParameterizedType {
    public static final JsonStringTypeSupportString INSTANCE = new JsonStringTypeSupportString();

    public JsonStringTypeSupportString() {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(Configuration.INSTANCE.getObjectMapperWrapper()));
    }

    public JsonStringTypeSupportString(Type javaType) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(Configuration.INSTANCE.getObjectMapperWrapper(), javaType));
    }

    public JsonStringTypeSupportString(Configuration configuration) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(configuration.getObjectMapperWrapper()), configuration);
    }

    public JsonStringTypeSupportString(ObjectMapper objectMapper) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(new ObjectMapperWrapper(objectMapper)));
    }

    public JsonStringTypeSupportString(ObjectMapperWrapper objectMapperWrapper) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(objectMapperWrapper));
    }

    public JsonStringTypeSupportString(ObjectMapper objectMapper, Type javaType) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(new ObjectMapperWrapper(objectMapper), javaType));
    }

    public JsonStringTypeSupportString(ObjectMapperWrapper objectMapperWrapper, Type javaType) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptorSupportString(objectMapperWrapper, javaType));
    }

    @Override
    public String getName() {
        return "json";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptorSupportString)this.getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
