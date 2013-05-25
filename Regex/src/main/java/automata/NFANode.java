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