package org.metahut.starfish.parser.antlr4.json;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JsonExtensionAnalyser {

    private static final Logger logger = LoggerFactory.getLogger(JsonExtensionAnalyser.class);

    public void visitor(String json) {
        CharStream input = CharStreams.fromString(json);
        JsonLexer lexer = new JsonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JsonParser parser = new JsonParser(tokens);
        JsonParser.JsonContext tree = parser.json();
        JsonExtensionVisitor tv = new JsonExtensionVisitor();
        Object visit = tv.visit(tree);
        logger.info(visit.toString());
    }
}
