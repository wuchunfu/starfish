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

package org.metahut.starfish.datasource.hive;

import org.metahut.starfish.datasource.api.DatasourceException;
import org.metahut.starfish.datasource.api.IDatasourceManager;
import org.metahut.starfish.datasource.common.JSONUtils;

import java.util.Objects;

import static org.metahut.starfish.datasource.hive.Constants.HIVE_DRIVER;

public class HiveDatasourceManager implements IDatasourceManager {

    @Override
    public String getType() {
        return "Hive";
    }

    @Override
    public HiveDatasourceParameter getDefaultParameter() {
        HiveDatasourceParameter hiveDatasourceParameter = new HiveDatasourceParameter();
        hiveDatasourceParameter.setDriverClassName(HIVE_DRIVER);

        return hiveDatasourceParameter;
    }

    @Override
    public HiveDatasource generateInstance(String parameter) {
        HiveDatasourceParameter hiveDatasourceParameter = JSONUtils.parseObject(parameter, HiveDatasourceParameter.class);
        if (Objects.isNull(hiveDatasourceParameter) || !hiveDatasourceParameter.checkParameter()) {
            throw new DatasourceException("hive datasource parameter check exception");
        }
        return new HiveDatasource(hiveDatasourceParameter);
    }
}
