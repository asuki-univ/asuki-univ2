package automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NFANode {
    private List<NFAEdge> edges;
    private boolean isFinal;

    public NFANode(boolean isFinal) {
        this.edges = new ArrayList<NFAEdge>();
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean flag) {
        this.isFinal = flag;
    }

    public void addEdge(NFANode node, Label label) {
        edges.add(new NFAEdge(node, label));
    }

    public List<NFAEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
}

class NFAEdge {
    private NFANode dest;
    private Label label;

    public NFAEdge(NFANode dest, Label label) {
        this.dest = dest;
        this.label = label;
    }

    public NFANode getDestination() {
        return dest;
    }

    public Label getLabel() {
        return label;
    }
}

class Label {
    private char c;

    static public Label newEpsilonLabel() {
        return new Label('\0');
    }

    static public Label newCharLabel(char c) {
        return new Label(c);
    }

    private Label(char c) {
        this.c = c;
    }

    public boolean isEmpty() {
        return c == '\0';
    }

    public boolean accepts(char c) {
        return this.c == c;
    }

    public char getChar() {
        return c;
    }
}
