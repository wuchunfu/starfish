package org.metahut.starfish.parser.function;

/**
 *
 */
interface LineAppender {

    /**
     * function extension for lines
     * @param lines
     */
    LineAppender appendLine(String... lines);

    /**
     * function extension for line
     * @param line
     * @return
     */
    LineAppender appendLine(String line);
}
