package org.metahut.starfish.unit;

public class EntityNameGentrator {

    private EntityNameGentrator() {
    }

    public static String generateName(String... args) {
        return String.join("@@", args);
    }
}
