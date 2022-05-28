//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.metahut.starfish.store.rdbms.type;

import com.vladmihalcea.hibernate.type.json.internal.JsonTypeDescriptor;
import com.vladmihalcea.hibernate.type.util.ObjectMapperWrapper;
import org.hibernate.type.descriptor.WrapperOptions;

import java.lang.reflect.Type;

public class JsonTypeDescriptorSupportString extends JsonTypeDescriptor {

    public JsonTypeDescriptorSupportString() {
    }

    public JsonTypeDescriptorSupportString(Type type) {
        super(type);
    }

    public JsonTypeDescriptorSupportString(ObjectMapperWrapper objectMapperWrapper) {
        super(objectMapperWrapper);
    }

    public JsonTypeDescriptorSupportString(ObjectMapperWrapper objectMapperWrapper, Type type) {
        super(objectMapperWrapper, type);
    }

    @Override
    public Object fromString(String string) {
        try {
            return super.fromString(string);
        } catch (Exception exception) {
            return string;
        }
    }

    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        try {
            return super.unwrap(value, type, options);
        } catch (Exception exception) {
            return (X) value;
        }
    }
}
