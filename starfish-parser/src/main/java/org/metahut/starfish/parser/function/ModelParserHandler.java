package org.metahut.starfish.parser.function;

import org.metahut.starfish.parser.domain.SymbolConstants;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.struct.StructWorker;
import org.metahut.starfish.parser.exception.AbstractMetaParserException;
import org.metahut.starfish.parser.exception.DataValidException;
import org.metahut.starfish.parser.exception.ModelValidException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class ModelParserHandler {

    private EnvironmentUnit environmentUnit;

    private FinalStorage metaStorage;

    public void valid(List<Class> structModels) throws AbstractMetaParserException {
        if (structModels == null || structModels.size() == 0) {
            throw new DataValidException("Non data in ,please check.");
        }
        Set<String> packages = new HashSet<>();
        for (Class structModel : structModels) {
            if (structModel.getPackagePath() == null || !structModel.getPackagePath().matches(SymbolConstants.PACKAGE_REGEX)) {
                throw new ModelValidException();
            }
            if (structModel.getName() == null || !structModel.getName().matches(SymbolConstants.CLASS_NAME_REGEX)) {
                throw new ModelValidException("ClassName not biaozhun");
            }
            if (!packages.add(structModel.getPackagePath())) {
                throw new ModelValidException("Repeat class");
            }
            if (structModel instanceof Class) {
                Class sfClass = (Class) structModel;
                if (sfClass.getAttributes() == null || sfClass.getAttributes().size() == 0) {
                    throw new ModelValidException();
                }
                for (Attribute sfAttribute : sfClass.getAttributes()) {
                    if (sfAttribute.getClassName() == null || !sfAttribute.getClassName().matches(SymbolConstants.FULL_CLASS_REGEX)) {
                        throw new ModelValidException();
                    }
                }
            }
        }
    }

    /**
     * 按照环境进行类加载
     * @param env
     * @param structModels
     */
    public void loadModels(String env,List<Class> structModels) {
        final String rewriteEnv = environmentUnit.rewrite(env);
        //1.version 没用 class按照类名来的
        //2.class
        //3. graph  nodes(contain id)  and links and category
        //classFullName
        structModels
                .stream()
                .filter(abstractStructModel -> abstractStructModel != null)
                .map(abstractStructModel -> {
                    String javaResource = ModelGenerator.toClassFile(rewriteEnv, abstractStructModel);
                    StructWorker structWorker = new StructWorker();
                    structWorker.setJavaResource(javaResource);

                    return null;
                })
                ;

        metaStorage.put(rewriteEnv,new MetaMap(structModels));
    }

    public void loadInstances(String env,List<?> insances) {

    }
}
