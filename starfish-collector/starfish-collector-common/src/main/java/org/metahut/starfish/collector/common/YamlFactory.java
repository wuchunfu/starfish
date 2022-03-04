package org.metahut.starfish.collector.common;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

import static org.metahut.starfish.collector.common.Constants.COLLECTOR_CONFIG_FILE;

public class YamlFactory {

    private YamlFactory() {
    }

    public static Properties getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;
        private Properties singleton;

        private Singleton() {
            singleton = loadYamlToProperties(COLLECTOR_CONFIG_FILE);
        }

        public Properties getInstance() {
            return singleton;
        }
    }

    public static String get(String key) {
        return getInstance().getProperty(key);
    }

    private static Properties loadYamlToProperties(String application) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(application));
        yaml.setSupportedTypes();
        return yaml.getObject();
    }

}
