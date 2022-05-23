package org.metahut.starfish.tools;

import org.metahut.starfish.api.dto.TypeRequestBatchCreateOrUpdateDTO;
import org.metahut.starfish.server.service.TypeService;
import org.metahut.starfish.server.utils.Assert;

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
            Assert.throwException(INIT_TYPE_MODEL_TO_LOAD_FILE_FAIL, null, e);
        }

        files.forEach(file -> {
            TypeRequestBatchCreateOrUpdateDTO typeRequest = JSONUtils.parseObject(file, TypeRequestBatchCreateOrUpdateDTO.class);
            typeService.initTypes(typeRequest);
        });

    }
}
