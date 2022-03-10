package org.metahut.starfish.parser.antlr4.json;

/**
 *  extends JsonBaseVisitor
 */
public class OctopusJsonVisitor {
    //@Override
    //public Object visitJson(JsonParser.JsonContext ctx) {
    //    return super.visitJson(ctx);
    //}
    //@Override
    //public Object visitObj(JsonParser.ObjContext ctx) {
    //    List<Object> result = new ArrayList<>();
    //    if (ctx != null && ctx.pair() != null) {
    //        Iterator<JsonParser.PairContext> iterator = ctx.pair().iterator();
    //        while (iterator.hasNext()) {
    //            JsonParser.PairContext next = iterator.next();
    //            Object object = visitPair(next);
    //            if (object instanceof List) {
    //                result.addAll((List)object);
    //            } else {
    //                result.add(object);
    //            }
    //        }
    //    }
    //    return result;
    //}
    //@Override
    //public Object visitPair(JsonParser.PairContext ctx) {
    //    String text = ctx.STRING().getText();
    //    Object key = text.substring(1,text.length() - 1);
    //    Object value = visitValue(ctx.value());
    //    return key;
    //}
    //@Override
    //public Object visitValue(JsonParser.ValueContext ctx) {
    //    int childCount = ctx.getChildCount();
    //    for (int i = 0; i < childCount; i++) {
    //        ParseTree child = ctx.getChild(i);
    //        if (child instanceof TerminalNode) {
    //            return child.getText();
    //        }
    //    }
    //    return super.visitValue(ctx);
    //}
    //@Override
    //public Object visitArr(JsonParser.ArrContext ctx) {
    //    List<Object> list = new ArrayList<>();
    //    if (ctx != null && ctx.value() != null) {
    //        Iterator<JsonParser.ValueContext> it = ctx.value().iterator();
    //        while(it.hasNext()) {
    //            JsonParser.ValueContext next = it.next();
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
}
