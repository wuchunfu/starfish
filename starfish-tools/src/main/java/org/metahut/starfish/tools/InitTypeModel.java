package org.metahut.starfish.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class InitTypeModel {
    public static void main(String[] args) {
        new SpringApplicationBuilder(InitTypeModel.class)
//                .profiles("init-type")
                .run(args);
    }

    @Component
//    @Profile("init-type")
    static class InitTypeModelRunner implements CommandLineRunner {
        private static final Logger logger = LoggerFactory.getLogger(InitTypeModelRunner.class);

        private final UpgradeService upgradeService;

        public InitTypeModelRunner(UpgradeService upgradeService) {
            this.upgradeService = upgradeService;
        }

        @Override
        public void run(String... args) {
            upgradeService.initTypeModel();
            logger.info("Type model initialization complete");
        }
    }
}
