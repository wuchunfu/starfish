package org.metahut.starfish.unit.enums;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class CategoryDeserializer extends JsonDeserializer<Category> {

    private List<String> linkCategoryNames = Arrays.stream(LinkCategory.values()).map(linkCategory -> linkCategory.name()).collect(Collectors.toList());

    private List<String> typeCategoryNames = Arrays.stream(TypeCategory.values()).map(typeCategory -> typeCategory.name()).collect(Collectors.toList());

    @Override
    public Category deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = jsonParser.getText();
        if (linkCategoryNames.contains(text)) {
            return LinkCategory.valueOf(text);
        }
        if (typeCategoryNames.contains(text)) {
            return TypeCategory.valueOf(text);
        }
        return null;
    }
}
