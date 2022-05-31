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

package org.metahut.starfish.server.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    private final MessageSource messageSource;

    public ValidatorConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //@Bean
    //public Validator validator() {
    //    LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
    //    factoryBean.setValidationMessageSource(messageSource);
        // 设置使用 HibernateValidator 校验器
        //factoryBean.setProviderClass(HibernateValidator.class);
        //Properties properties = new Properties();
        // 设置 快速异常返回
        //properties.setProperty("hibernate.validator.fail_fast", "true");
        //factoryBean.setValidationProperties(properties);
        // 加载配置
        //factoryBean.afterPropertiesSet();
    //    return factoryBean;
    //}

}
