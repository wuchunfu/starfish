package org.metahut.starfish.server.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.util.Map;

@SpringBootTest
@Disabled
public class BeanToMapConverterTest {

    @Autowired
    private ConversionService conversionService;

    @Test
    public void testBeanToMap() {
        Pair pair = new Pair();
        pair.setCode(1L);
        pair.setName("1n");
        Map<String, Object> convert = conversionService.convert(pair, Map.class);
        Assertions.assertNotNull(convert);
    }

    public static class Pair {
        private Long code;
        private String name;

        public Long getCode() {
            return code;
        }

        public void setCode(Long code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
