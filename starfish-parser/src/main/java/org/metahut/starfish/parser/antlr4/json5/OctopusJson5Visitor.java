package org.metahut.starfish.parser.antlr4.json5;

/**
 * extends Json5BaseVisitor
 */
public class OctopusJson5Visitor  {

    private static final String VARIABLE_ID = "id";

    //1.输出 重写
    //2.
    //mainId
    //root id ?

    //@Override
    //public Object visitJson5(Json5Parser.Json5Context ctx) {
    //    Object obj = super.visitJson5(ctx);
    //    return obj;
    //}
    //@Override
    //public Object visitObj(Json5Parser.ObjContext ctx) {
    //    List<Object> result = new ArrayList<>();
    //    if (ctx != null && ctx.pair() != null) {
    //        Iterator<Json5Parser.PairContext> iterator = ctx.pair().iterator();
    //        while (iterator.hasNext()) {
    //            Json5Parser.PairContext next = iterator.next();
    //            Object object = visitPair(next);
    //            if (object instanceof List) {
    //                result.addAll((List)object);
    //            } else {
    //                result.add(object);
    //            }
    //        }
    //        ctx.getParent().addChild(splitNode());
    //        ctx.getParent().addChild(toObj(UUID.randomUUID().toString().replaceAll("-","")));
    //    }
    //    if (ctx.children .size() > 2) {
    //        ctx.children.add(1, splitNode());
    //    }
    //    ctx.children.add(1, toObj(UUID.randomUUID().toString().replaceAll("-", "")));
    //    return result;
    //}
    //@Override
    //public Object visitPair(Json5Parser.PairContext ctx) {
    //    Object key = visitKey(ctx.key());
    //    if ("b".equals(key)) {
    //    }
    //    Object value = visitValue(ctx.value());
    //    return key;
    //}
    //@Override
    //public Object visitKey(Json5Parser.KeyContext ctx) {
    //    List<Object> list = new ArrayList<>();
    //    int childCount = ctx.getChildCount();
    //    for (int i = 0; i < childCount; i++) {
    //        ParseTree child = ctx.getChild(i);
    //        if (child instanceof TerminalNodeImpl) {
    //            TerminalNodeImpl terminalNode = (TerminalNodeImpl) child;
    //            String text = child.getText();
    //            if (terminalNode.getSymbol().getType() == Json5Lexer.STRING) {
    //                text = text.substring(1,text.length()-1);
    //            }
    //            //TODO key 校验，应该跟javaProperty是一种正则
    //            list.add(text);
    //        }
    //    }
    //    return list;
    //}
    //@Override
    //public Object visitValue(Json5Parser.ValueContext ctx) {
    //    int childCount = ctx.getChildCount();
    //    for (int i = 0; i < childCount; i++) {
    //        ParseTree child = ctx.getChild(i);
    //        if (child instanceof TerminalNode) {
    //            return child.getText();
    //        }
    //        if (child instanceof Json5Parser.ObjContext) {
    //            ((Json5Parser.ObjContext) child).addChild(toObj(UUID.randomUUID().toString().replaceAll("-","")));
    //        }
    //    }
    //    return super.visitValue(ctx);
    //}
    //@Override
    //public Object visitArr(Json5Parser.ArrContext ctx) {
    //    List<Object> list = new ArrayList<>();
    //    if (ctx != null && ctx.value() != null) {
    //        Iterator<Json5Parser.ValueContext> it = ctx.value().iterator();
    //        while(it.hasNext()) {
    //            Json5Parser.ValueContext next = it.next();
    //            Object object = visitValue(next);
    //            if (object instanceof List) {
    //                list.addAll((List)object);
    //            } else {
    //                list.add(object);
    //            }
    //        }
    //    }
    //    return list;
    //}
    //@Override
    //public Object visitNumber(Json5Parser.NumberContext ctx) {
    //    return ctx.NUMBER().toString();
    //}
    //private TerminalNode splitNode() {
    //    Token token = new Json5Lexer(CharStreams.fromString(",")).getAllTokens().get(0);
    //    TerminalNode terminalNode = new TerminalNodeImpl(token);
    //    //        new CommonTokenFactory().create(,",")
    //    return terminalNode;
    //}
    //private RuleContext toObj(String id) {
    //    CharStream input = CharStreams.fromString(String.format("\"%s\":\"%s\"",VARIABLE_ID,id));
    //    Json5Lexer lexer = new Json5Lexer(input);
    //    CommonTokenStream tokens = new CommonTokenStream(lexer);
    //    Json5Parser parser = new Json5Parser(tokens);
    //    Json5Parser.PairContext value = parser.pair();
    //    return value;
    //}
}
