package org.metahut.starfish.ingestion.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.metahut.starfish")
public class IngestionApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(IngestionApplication.class)
                .profiles("ingestion")
                .run(args);
    }
}
