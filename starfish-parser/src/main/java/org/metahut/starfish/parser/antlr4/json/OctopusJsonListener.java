package org.metahut.starfish.parser.antlr4.json;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author XuYang
 * Create at 2022/3/3
 * @description
 */
public class OctopusJsonListener extends JsonBaseListener{

    @Override
    public void enterJson(JsonParser.JsonContext ctx) {
        super.enterJson(ctx);
    }

    @Override
    public void exitJson(JsonParser.JsonContext ctx) {
        super.exitJson(ctx);
    }

    @Override
    public void enterObj(JsonParser.ObjContext ctx) {
        super.enterObj(ctx);
    }

    @Override
    public void exitObj(JsonParser.ObjContext ctx) {
        super.exitObj(ctx);
    }

    @Override
    public void enterPair(JsonParser.PairContext ctx) {
        super.enterPair(ctx);
    }

    @Override
    public void exitPair(JsonParser.PairContext ctx) {
        super.exitPair(ctx);
    }

    @Override
    public void enterArr(JsonParser.ArrContext ctx) {
        super.enterArr(ctx);
    }

    @Override
    public void exitArr(JsonParser.ArrContext ctx) {
        super.exitArr(ctx);
    }

    @Override
    public void enterValue(JsonParser.ValueContext ctx) {
        super.enterValue(ctx);
    }

    @Override
    public void exitValue(JsonParser.ValueContext ctx) {
        super.exitValue(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
