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
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

/**
 *  utils to analysis instance and relations based on type info
 */
public class JsonExtensionVisitor<K,T> extends JsonBaseVisitor {

    private static final String REF = "$ref";

    private static final String JSON_PATH = "@|(\\.\\.(/\\.\\.)*)|(\\$(\\.(\\S)+([\\d+])?)*)";

    private static final Logger LOG = LoggerFactory.getLogger(JsonExtensionVisitor.class);

    private final Map<K, Class> classInfos;

    private String rootClassName;

    private final Stack<StackLevel> stack;

    private List<LinkRel> links;

    private InstanceAnalysisStruct<K,T> analysisResultStruct;

    @Override
    public Object visitJson(JsonParser.JsonContext ctx) {
        pushNewStackLevel(rootClassName,"$");
        super.visitJson(ctx);
        popStackLevel();
        for (LinkRel link : links) {
            analysisResultStruct.getLinks().add(new InstanceAnalysisStruct.Link<>(
                    analysisResultStruct.getInstances().get(link.getStartPath()),
                    analysisResultStruct.getInstances().get(link.getEndPath()),
                    link.getAttribute()
            ));
        }
        return null;
    }

    @Override
    public Object visitObj(JsonParser.ObjContext ctx) {
        currentStack().addObject();
        super.visitObj(ctx);
        StackLevel currentStack = currentStack();
        StackLevel.StackBlock stackBlock = currentStack.currentObject();
        if (!stackBlock.isRepeat()) {
            InstanceAnalysisStruct.Instance<K,T> kInstance = this.analysisResultStruct.getInstances().get(stackBlock.path());
            if (kInstance == null) {
                InstanceAnalysisStruct.Instance<K,T> instance = new InstanceAnalysisStruct.Instance<>();
                instance.setProperties(stackBlock.getProperties());
                instance.setPath(stackBlock.path());
                instance.setName(getNameFromEntity(currentStack.getClassInfo().getValue(), stackBlock.getProperties()));
                instance.setTypeId(currentStack.getClassInfo().getKey());
                this.analysisResultStruct.getInstances().put(stackBlock.path(), instance);
            }
        }
        return null;
    }

    @Override
    public Object visitPair(JsonParser.PairContext ctx) {
        StackLevel currentStack = currentStack();
        StackLevel.StackBlock currentBlock = currentStack.currentObject();
        String name = jsonEscape(ctx.STRING());
        Class classInfo = currentStack.getClassInfo().getValue();
        if (REF.equals(name)) {
            currentBlock.setRepeat(true);
            String jsonPath = jsonEscape(ctx.value());
            //find by json path
            //link two nodes
            storeLink(currentBlock.path(),jsonPath);
            return null;
        }
        Attribute attribute = classInfo.findAttributeByName(name);
        if (attribute != null) {
            if (RelType.ENTITY == attribute.getRelType()) {
                //TODO link two type
                pushNewStackLevel(attribute.getClassName(),currentBlock.path() + "." + name);
                super.visitValue(ctx.value());
                for (StackLevel.StackBlock block : currentStack().blocks) {
                    if (!block.isRepeat()) {
                        storeLink(currentBlock.path(), block.path(), name);
                    }
                }
                popStackLevel();
            } else {
                currentBlock.getProperties().put(name,(T)jsonEscape(ctx.value()));
            }
        } else {
            LOG.warn("{} property {} undefined.",classInfo.fullClassName(),name);
        }
        return null;
    }

    @Override
    public Object visitValue(JsonParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }

    @Override
    public Object visitArr(JsonParser.ArrContext ctx) {
        currentStack().setArray(true);
        if (ctx != null && ctx.value() != null) {
            Iterator<JsonParser.ValueContext> it = ctx.value().iterator();
            while (it.hasNext()) {
                JsonParser.ValueContext next = it.next();
                super.visitValue(next);
            }
        }
        return null;
    }

    class StackLevel {
        private String path;

        private Map.Entry<K, Class> classInfo;

        private int index = 0;

        private boolean array = false;

        private Stack<StackBlock> blocks = new Stack<>();

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
            this.blocks.push(new StackBlock(index++));
        }

        public int index() {
            return this.blocks.size() - 1;
        }

        public StackBlock currentObject() {
            return this.blocks.get(index());
        }

        public boolean isArray() {
            return array;
        }

        public void setArray(boolean array) {
            this.array = array;
        }

        class StackBlock {

            private boolean repeat;

            private final int index;

            private Map<String,T> properties = new HashMap<>();

            public Map<String, T> getProperties() {
                return properties;
            }

            public void setProperties(Map<String, T> properties) {
                this.properties = properties;
            }

            public String path() {
                if (array) {
                    return path + "[" + index + "]";
                } else {
                    return path;
                }
            }

            public StackBlock(int index) {
                this.index = index;
            }

            public boolean isRepeat() {
                return repeat;
            }

            public void setRepeat(boolean repeat) {
                this.repeat = repeat;
            }
        }
    }

    class LinkRel {
        private String startPath;
        private String endPath;
        private String attribute;

        public String getStartPath() {
            return startPath;
        }

        public void setStartPath(String startPath) {
            this.startPath = startPath;
        }

        public String getEndPath() {
            return endPath;
        }

        public void setEndPath(String endPath) {
            this.endPath = endPath;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
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

    private String getNameFromEntity(Class classInfo,Map<String,T> property) {
        String nameAttributeRel = classInfo.getNameAttributeRel();
        Object value = property.get(nameAttributeRel);
        if (value == null || "".equals(value)) {
            throw new InstanceNameNullException(String.format("Type:%s,NameProperty:%s,GivenValueInfo:%s",classInfo.fullClassName(),classInfo.getPackagePath(),property));
        } else {
            return value.toString();
        }
    }

    private void storeLink(String startPath,String endPath,String attribute) {
        for (LinkRel link : links) {
            if (startPath.equals(link.getStartPath()) && endPath.equals(link.getEndPath()) && attribute.equals(link.getAttribute())) {
                return;
            }
        }
        LinkRel link = new LinkRel();
        link.setStartPath(startPath);
        link.setEndPath(endPath);
        link.setAttribute(attribute);
        this.links.add(link);
    }

    private void storeLink(String path,String endPath) {
        int index = path.lastIndexOf(".");
        String startPath = path.substring(0,index);
        String attribute = path.substring(index + 1);
        storeLink(startPath,endPath,attribute);
    }

    private String jsonEscape(TerminalNode node) {
        String fullText = node.getText();
        return StringEscapeUtils.escapeJson(fullText.substring(1,fullText.length() - 1));
    }

    private String jsonEscape(JsonParser.ValueContext node) {
        String fullText = node.getText();
        return StringEscapeUtils.escapeJson(fullText.substring(1,fullText.length() - 1));
    }

    private StackLevel currentStack() {
        return this.stack.get(this.stack.size() - 1);
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

    private JsonExtensionVisitor(String rootClassName,Map<K, Class> classInfos) {
        this.classInfos = classInfos;
        this.rootClassName = rootClassName;
        this.stack = new Stack<>();
        this.links = new ArrayList<>();
        this.analysisResultStruct = new InstanceAnalysisStruct<>();
        this.analysisResultStruct.setInstances(new HashMap<>());
        this.analysisResultStruct.setLinks(new ArrayList<>());
    }

    public static <K> InstanceAnalysisStruct analysis(String json,String rootClassName,Map<K,Class> classInfos) {
        CharStream input = CharStreams.fromString(json);
        JsonLexer lexer = new JsonLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JsonParser parser = new JsonParser(tokens);
        JsonParser.JsonContext tree = parser.json();
        JsonExtensionVisitor jsonExtensionVisitor = new JsonExtensionVisitor(rootClassName,classInfos);
        jsonExtensionVisitor.analysisResultStruct.setInstances(new HashMap<>());
        jsonExtensionVisitor.analysisResultStruct.setLinks(new ArrayList<>());
        jsonExtensionVisitor.visit(tree);
        return jsonExtensionVisitor.analysisResultStruct;
    }
}
