package org.metahut.starfish.parser.json5;

import org.junit.jupiter.api.Test;

/**
 * @author XuYang
 * Create at 2022/3/3
 * @description
 */
public class OctopusJson5AnalyserTest {

    @Test
    public void visitor() {
        OctopusJson5Analyser analyser = new OctopusJson5Analyser();
        //,'c':{'e':1,'f':2}
        analyser.visitor("{\"a\":1,\"b\":true}");
    }
}
