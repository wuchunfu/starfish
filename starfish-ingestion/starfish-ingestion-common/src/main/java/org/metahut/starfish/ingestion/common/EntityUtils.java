package org.metahut.starfish.ingestion.common;

public class EntityUtils {

    public static String generateName(String... args) {
        return String.join("@@", args);
    }
}
