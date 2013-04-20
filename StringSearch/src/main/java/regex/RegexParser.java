package regex;

abstract class RENode {
    abstract NFA createAutomata();
}

class RECharacter extends RENode {
    private char c;

    public RECharacter(char c) {
        this.c = c;
    }

    @Override
    NFA createAutomata() {
        NFANode beginNode = new NFANode(false);
        NFANode endNode = new NFANode(true);
        beginNode.addEdge(endNode, Label.newCharLabel(c));

        return new NFA(beginNode, endNode);
    }
}

class REStar extends RENode {
    private RENode node;

    public REStar(RENode node) {
        this.node = node;
    }

    @Override
    NFA createAutomata() {
        NFA am = node.createAutomata();
        am.getBeginNode().addEdge(am.getEndNode(), Label.newEmptyLabel());
        am.getEndNode().addEdge(am.getBeginNode(), Label.newEmptyLabel());

        return am;
    }
}

class REOptional extends RENode {
    private RENode node;

    public REOptional(RENode node) {
        this.node = node;
    }

    @Override
    NFA createAutomata() {
        NFA am = node.createAutomata();
        am.getBeginNode().addEdge(am.getEndNode(), Label.newEmptyLabel());        
        return am;
    }
}

class RESequence extends RENode {
    private RENode r1;
    private RENode r2;

    public RESequence(RENode r1, RENode r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    NFA createAutomata() {
        NFA am1 = r1.createAutomata();
        NFA am2 = r2.createAutomata();
        
        am1.getEndNode().setFinal(false);
        am1.getEndNode().addEdge(am2.getBeginNode(), Label.newEmptyLabel());
        
        return new NFA(am1.getBeginNode(), am2.getEndNode());
    }
}

class RESelection extends RENode {
    private RENode r1;
    private RENode r2;

    public RESelection(RENode r1, RENode r2) {
        this.r1 = r1;
        this.r2 = r2;
    }
    
    @Override
    NFA createAutomata() {
        NFA am1 = r1.createAutomata();
        NFA am2 = r2.createAutomata();
        
        NFANode beginNode = new NFANode(false);
        NFANode endNode = new NFANode(true);
        
        am1.getEndNode().setFinal(false);
        am2.getEndNode().setFinal(false);
        beginNode.addEdge(am1.getBeginNode(), Label.newEmptyLabel());
        beginNode.addEdge(am2.getBeginNode(), Label.newEmptyLabel());
        am1.getEndNode().addEdge(endNode, Label.newEmptyLabel());
        am2.getEndNode().addEdge(endNode, Label.newEmptyLabel());
        
        return new NFA(beginNode, endNode);
    }
}

// (), *, ?, | のみを受け取る。英字のみを考慮する
// simple ::= ( r )
//         |  alpha
// c1     ::= simple ?
//         |  simple *
//         |  simple
// c2     ::= c1 r
//         |  c1
// r      ::= c1 | r
//         |  c2
// 
public class RegexParser {
    private static final char[] META_CHARACTERS = new char[] {
        '(', ')', '*', '?', '|'
    };

    private String regex;
    private int pos;

    public RegexParser(String regex) {
        this.regex = regex;
        this.pos = 0;
    }

    private static boolean isMetaCharacter(char c) {
        for (char meta : META_CHARACTERS) {
            if (c == meta)
                return true;
        }

        return false;
    }

    public RENode parse() {
        RENode node = parseRegex(); 
        if (pos != regex.length())
            return null;

        return node;
    }

    private RENode parseRegex() {
        if (regex.length() <= pos)
            return null;

        RENode r1 = parseSeq();
        if (regex.length() <= pos || regex.charAt(pos) != '|')
            return r1;

        ++pos;
        RENode r2 = parseRegex();
        return new RESelection(r1, r2);
    }

    private RENode parseSeq() {
        if (regex.length() <= pos)
            return null;

        RENode r1 = parseAdditional();
        if (r1 == null)
            return null;

        RENode r2 = parseSeq();
        if (r2 == null)
            return r1;
        return new RESequence(r1, r2);
    }

    private RENode parseAdditional() {
        if (regex.length() <= pos)
            return null;

        RENode simple = parseSimple();
        if (regex.length() <= pos)
            return simple;
        if (regex.charAt(pos) == '?') {
            ++pos;
            return new REOptional(simple);
        } else if (regex.charAt(pos) == '*') {
            ++pos;
            return new REStar(simple);
        } else {
            return simple;
        }
    }

    private RENode parseSimple() {
        if (regex.length() <= pos)
            return null;

        char c = regex.charAt(pos);
        if (c == '(') {
            ++pos;
            RENode node = parseRegex();
            if (node == null)
                throw new IllegalArgumentException();
            if (regex.charAt(pos++) != ')')
                throw new IllegalArgumentException();
            return node;
        } else if (isMetaCharacter(c)) {
            return null;
        } else {
            ++pos;
            return new RECharacter(c);
        }
    }
}
