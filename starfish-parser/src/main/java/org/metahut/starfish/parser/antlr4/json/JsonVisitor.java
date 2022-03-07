// Generated from /Users/xuyang/JavaProject/starfish/starfish-parser/src/main/java/org/metahut/starfish/parser/antlr4/json/Json.g4 by ANTLR 4.9.1
package org.metahut.starfish.parser.antlr4.json;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JsonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JsonVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JsonParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJson(JsonParser.JsonContext ctx);
	/**
	 * Visit a parse tree produced by {@link JsonParser#obj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObj(JsonParser.ObjContext ctx);
	/**
	 * Visit a parse tree produced by {@link JsonParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(JsonParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link JsonParser#arr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArr(JsonParser.ArrContext ctx);
	/**
	 * Visit a parse tree produced by {@link JsonParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(JsonParser.ValueContext ctx);
}