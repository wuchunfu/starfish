/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
