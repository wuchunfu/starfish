package org.metahut.starfish;

import org.metahut.starfish.tools.UpgradeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class InitTypeModel {
    public static void main(String[] args) {
        new SpringApplicationBuilder(InitTypeModel.class)
                .profiles("init-type")
                .run(args);
    }

    @Component
    @Profile("init-type")
    static class InitTypeModelRunner implements CommandLineRunner {
        private static final Logger LOGGER = LoggerFactory.getLogger(InitTypeModelRunner.class);

        private final ConfigurableApplicationContext context;
        private final UpgradeService upgradeService;

        public InitTypeModelRunner(ConfigurableApplicationContext context, UpgradeService upgradeService) {
            this.context = context;
            this.upgradeService = upgradeService;
        }

        @Override
        public void run(String... args) {
            upgradeService.initTypeModel();
            LOGGER.info("Type model initialization complete");
            SpringApplication.exit(context);
        }
    }
}
