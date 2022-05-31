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

package org.metahut.starfish.tools;

import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.server.utils.Assert;
import org.metahut.starfish.server.utils.JSONUtils;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.metahut.starfish.api.enums.Status.INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL;

@Service
public class UpgradeServiceImpl implements UpgradeService {

    private final TypeService typeService;

    public UpgradeServiceImpl(TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public void initTypeModel() {
        List<File> files = null;
        try {
            String path = ResourceUtils.getURL("tools/models").getPath();
            files = Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            Assert.throwException(e, INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL);
        }

        files.forEach(file -> {
            TypeRequestBatchCreateOrUpdateDTO typeRequest = JSONUtils.parseObject(file, TypeRequestBatchCreateOrUpdateDTO.class);
            typeService.initTypes(typeRequest);
        });

    }
}
