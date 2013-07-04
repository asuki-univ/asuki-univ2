package automata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NFANode {
    private Map<Character, List<NFANode>> edges;
    private List<NFANode> epsilonEdgeDestinations;

    private boolean isFinal;

    public NFANode(boolean isFinal) {
        this.edges = new HashMap<Character, List<NFANode>>();
        this.epsilonEdgeDestinations = new ArrayList<NFANode>();
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean flag) {
        this.isFinal = flag;
    }

    public void addEdge(char c, NFANode node) {
        List<NFANode> es = edges.get(c);
        if (es == null) {
            es = new ArrayList<NFANode>();
            edges.put(c, es);
        }

        es.add(node);
    }

    public void addEpsilonEdge(NFANode node) {
        epsilonEdgeDestinations.add(node);
    }

    public List<NFANode> getDestinations(char c) {
        List<NFANode> dests = edges.get(c);
        if (dests != null)
            return dests;

        return Collections.emptyList();
    }

    public Map<Character, List<NFANode>> getEdges() {
        return edges;
    }

    public List<NFANode> getEpsilonEdgeDestinations() {
        return this.epsilonEdgeDestinations;
    }
}
