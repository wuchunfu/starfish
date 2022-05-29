package org.metahut.starfish.parser.antlr4.json;

import org.metahut.starfish.parser.domain.enums.RelType;
import org.metahut.starfish.parser.domain.instance.Attribute;
import org.metahut.starfish.parser.domain.instance.Class;
import org.metahut.starfish.parser.domain.instance.InstanceAnalysisStruct;
import org.metahut.starfish.parser.exception.InstanceNameNullException;
import org.metahut.starfish.parser.exception.TypeNotPresentException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 * 1。2， json
 */
public class JsonExtensionListener<K,T> extends JsonBaseListener {

    private static final String REF = "$ref";

    private static final String JSON_PATH = "@|(\\.\\.(/\\.\\.)*)|(\\$(\\.(\\S)+([\\d+])?)*)";

    private static final Logger LOG = LoggerFactory.getLogger(JsonExtensionListener.class);

    private final Map<K, Class> classInfos;

    private String rootClassName;

    private final Stack<StackLevel> stack;

    private InstanceAnalysisStruct<K,T> analysisResultStruct;

    private final Stack<Object> valueStack;

    /**
     * for jsonPath
     * {“$ref”:”\$”}
     * {“$ref”:”@”}
     * {“$ref”:”..”}
     * {“$ref”:”../..”}
     * {“$ref”:”\$.members[0].reportTo”}
     */
    private Map<String,InstanceAnalysisStruct<K,T>> routeMapping;

    @Override
    public void enterJson(JsonParser.JsonContext ctx) {
        pushNewStackLevel(rootClassName,"$");
    }

    @Override
    public void exitJson(JsonParser.JsonContext ctx) {
        StackLevel pop = popStackLevel();
        //
    }

    @Override
    public void enterObj(JsonParser.ObjContext ctx) {
        currentStack().addObject();
    }

    @Override
    public void exitObj(JsonParser.ObjContext ctx) {
        StackLevel currentStack = currentStack();
        //TODO path
        StackBlock stackBlock = currentStack.removeObject();
        InstanceAnalysisStruct.Instance<K,T> kInstance = this.analysisResultStruct.getInstances().get(currentStack.getPath());
        if (kInstance == null) {
            InstanceAnalysisStruct.Instance<K,T> instance = new InstanceAnalysisStruct.Instance<>();
            instance.setProperties(stackBlock.getProperties());
            instance.setPath(currentStack.getPath());
            instance.setQualifiedName(getNameFromEntity(currentStack.getClassInfo().getValue(), stackBlock.getProperties()));
            instance.setTypeId(currentStack.getClassInfo().getKey());
            this.analysisResultStruct.getInstances().put(currentStack.getPath(),instance);
        }
    }

    @Override
    public void enterPair(JsonParser.PairContext ctx) {
        StackLevel currentStack = currentStack();
        StackBlock currentBlock = currentStack.currentObject();
        String name = jsonEscape(ctx.STRING());
        Class classInfo = currentStack.getClassInfo().getValue();
        //TODO match $ or .. or @
        if (REF.equals(name)) {
            //TODO link
            String jsonPath = ctx.value().getText();
            //find by json path
            //link two nodes
            return;
        }
        Attribute attribute = classInfo.findAttributeByName(name);
        if (attribute != null) {
            if (RelType.ENTITY == attribute.getRelType()) {
                //TODO link two type
                pushNewStackLevel(attribute.getClassName(),currentStack.getPath() + "." + name);
            } else {
                currentBlock.getProperties().put(name,valueAnalysis(ctx.value()));
            }
        } else {
            LOG.warn("{} property {} undefined.",classInfo.fullClassName(),name);
        }
    }

    public String jsonEscape(TerminalNode node) {
        String fullText = node.getText();
        return StringEscapeUtils.escapeJson(fullText.substring(1,fullText.length() - 1));
    }

    @Override
    public void exitPair(JsonParser.PairContext ctx) {
        super.exitPair(ctx);
    }

    @Override
    public void enterArr(JsonParser.ArrContext ctx) {
        currentStack().setArray(true);
        if (ctx != null && ctx.value() != null) {
            Iterator<JsonParser.ValueContext> it = ctx.value().iterator();
            while (it.hasNext()) {
                JsonParser.ValueContext next = it.next();
                super.enterValue(next);
            }
        }
    }

    @Override
    public void exitArr(JsonParser.ArrContext ctx) {
        super.exitArr(ctx);
    }

    @Override
    public void enterValue(JsonParser.ValueContext ctx) {
        super.enterValue(ctx);
    }

    public Object valueAnalysis(JsonParser.ValueContext ctx) {
        return ctx.getText();
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

    private JsonExtensionListener(String rootClassName,Map<K, Class> classInfos) {
        this.classInfos = classInfos;
        this.rootClassName = rootClassName;
        this.stack = new Stack<>();
        this.valueStack = new Stack<>();
        this.analysisResultStruct = new InstanceAnalysisStruct<>();
        this.analysisResultStruct.setInstances(new HashMap<>());
        this.analysisResultStruct.setLinks(new ArrayList<>());
    }

    class StackLevel {
        private String path;

        private Map.Entry<K, Class> classInfo;

        private int index = 0;

        private boolean array = false;

        private Stack<StackBlock> blocks = new Stack<>();

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Map.Entry<K, Class> getClassInfo() {
            return classInfo;
        }

        public void setClassInfo(Map.Entry<K, Class> classInfo) {
            this.classInfo = classInfo;
        }

        public void addObject() {
            this.blocks.push(new StackBlock());
        }

        public StackBlock removeObject() {
            return this.blocks.pop();
        }

        public void addProperty(String name,Object value) {
            this.blocks.get(this.blocks.size() - 1).getProperties().put(name,value);
        }

        public void forward() {
            this.index++;
        }

        public void back() {
            this.index--;
        }

        public StackBlock currentObject() {
            return this.blocks.get(index);
        }

        public String currentPath() {
            return path + "[" + index + "]";
        }

        public boolean isArray() {
            return array;
        }

        public void setArray(boolean array) {
            this.array = array;
        }
    }

    class StackBlock<T> {

        private Map<String,T> properties = new HashMap<>();

        public Map<String, T> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, T> properties) {
            this.properties = properties;
        }
    }

    private StackLevel pushNewStackLevel(String className,String path) {
        StackLevel stackLevel = new StackLevel();
        stackLevel.setClassInfo(findClassByName(className));
        stackLevel.setPath(path);
        this.stack.push(stackLevel);
        return stackLevel;
    }

    private StackLevel popStackLevel() {
        return this.stack.pop();
    }

    private String getNameFromEntity(Class classInfo,Map<String,Object> property) {
        String nameAttributeRel = classInfo.getNameAttributeRel();
        Object value = property.get(nameAttributeRel);
        if (value == null || "".equals(value)) {
            throw new InstanceNameNullException(String.format("Type:%s,NameProperty:%s,GivenValueInfo:%s",classInfo.fullClassName(),classInfo.getPackagePath(),property));
        } else {
            return value.toString();
        }
    }

    private StackLevel currentStack() {
        return this.stack.get(this.stack.size() - 1);
    }

    public static <K> InstanceAnalysisStruct analysis(String json,String rootClassName,Map<K,Class> classInfos) {
        CharStream input = CharStreams.fromString(json);
        JsonLexer lexer = new JsonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JsonParser parser = new JsonParser(tokens);
        JsonParser.JsonContext tree = parser.json();
        ParseTreeWalker walker = new ParseTreeWalker();
        JsonExtensionListener jsonExtensionVisitor = new JsonExtensionListener(rootClassName,classInfos);
        walker.walk(jsonExtensionVisitor,tree);
        return jsonExtensionVisitor.analysisResultStruct;
    }

    private Map.Entry<K, Class> findClassByName(String fullClassName) throws TypeNotPresentException {
        Optional<Map.Entry<K, Class>> typeInfoEntry =
                classInfos
                .entrySet()
                .stream()
                .filter(entry -> fullClassName.equals(entry.getValue().fullClassName()))
                .findFirst();
        if (typeInfoEntry.isPresent()) {
            return typeInfoEntry.get();
        } else {
            throw new TypeNotPresentException(fullClassName);
        }
    }
}
