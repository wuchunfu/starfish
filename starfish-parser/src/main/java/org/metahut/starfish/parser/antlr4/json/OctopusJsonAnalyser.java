package org.metahut.starfish.parser.antlr4.json;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author XuYang
 * Create at 2022/3/3
 * @description
 */
public class OctopusJsonAnalyser {

    public void visitor(String json) {
        CharStream input = CharStreams.fromString(json);
        JsonLexer lexer = new JsonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JsonParser parser = new JsonParser(tokens);
        JsonParser.JsonContext tree = parser.json();
        OctopusJsonVisitor tv = new OctopusJsonVisitor();
        Object object = tv.visit(tree);
        System.out.println(object);
    }
}
