package org.metahut.starfish.parser.antlr4.json5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class OctopusJson5AnalyserTest {

    @Test
    public void visitor1() {
        OctopusJson5Analyser analyser = new OctopusJson5Analyser();
        //
        analyser.visitor("{\"a\":1,\"b\":true,'c':{'e':1,'f':2,g:[{m:3},{n:4}]}}");
    }

    @Test
    public void visitor2() {
        OctopusJson5Analyser analyser = new OctopusJson5Analyser();
        //
        String json = "{\"\\\"a\\\"\":1,\"b\":true,'c':{e:1,'f':2,g:[{m:3},{n:4},{}]}}";
        System.out.println(json);
        analyser.visitor(json);
    }
}
