package org.metahut.starfish.parser.antlr4.json;

import org.junit.jupiter.api.Test;

/**
 *
 */
public class OctopusJsonAnalyserTest {

    @Test
    public void visitor1() {
        OctopusJsonAnalyser analyser = new OctopusJsonAnalyser();
        //
        analyser.visitor("{\"a\":1,\"b\":true,\"c\":{\"e\":1,\"f\":2,\"g\":[{\"m\":3},{\"n\":4}]}}");
    }

    @Test
    public void visitor2() {
        OctopusJsonAnalyser analyser = new OctopusJsonAnalyser();
        //
        analyser.visitor("{\"a\":1,\"b\":true,\"c\":{\"e\":1,\"f\":2,\"g\":[{\"m\":3},{\"n\":4}]}}");
    }
}
