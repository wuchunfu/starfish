package org.metahut.starfish.parser.antlr4.json;

import org.junit.jupiter.api.Test;

/**
 *
 */
public class JsonExtensionAnalyserTest {

    @Test
    public void visitor1() {
        JsonExtensionAnalyser analyser = new JsonExtensionAnalyser();
        //
        analyser.visitor("{\"a\":1,\"b\":true,\"c\":{\"e\":1,\"f\":2,\"g\":[{\"m\":3},{\"n\":4}]}}");
    }

    @Test
    public void visitor2() {
        JsonExtensionAnalyser analyser = new JsonExtensionAnalyser();
        //
        analyser.visitor("{\"a\":1,\"b\":true,\"c\":{\"e\":1,\"f\":2,\"g\":[{\"m\":3},{\"n\":4}]}}");
    }
}
