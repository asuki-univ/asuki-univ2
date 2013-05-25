package automata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DFANode {
    private Map<Character, DFANode> edges;
    private boolean isFinal;

    public DFANode(boolean isFinal) {
        this.edges = new HashMap<Character, DFANode>();
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean flag) {
        this.isFinal = flag;
    }

    public void addEdge(char c, DFANode node) {
        if (edges.containsKey(c)) {
            if (edges.get(c) != node)
                throw new IllegalArgumentException();
            return;
        }

        edges.put(c, node);
    }

    public DFANode transit(char c) {
        return edges.get(c);
    }

    public Map<Character, DFANode> getEdges() {
        return Collections.unmodifiableMap(edges);
    }
}