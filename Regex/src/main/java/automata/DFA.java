package automata;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public class DFA {
    private DFANode beginNode;

    public DFA(DFANode beginNode) {
        this.beginNode = beginNode;
    }

    public DFANode getBeginNode() {
        return beginNode;
    }

    public boolean accepts(String s) {
        DFANode node = beginNode;

        for (int i = 0; i < s.length(); ++i) {
            node = node.transit(s.charAt(i));
            if (node == null)
                return false;
        }

        return node.isFinal();
    }

    public int getNodeSize() {
        return makeNodeList().size();
    }

    public List<DFANode> makeNodeList() {
        Set<DFANode> visited = new HashSet<DFANode>();
        Queue<DFANode> q = new ArrayDeque<DFANode>();
        q.add(beginNode);
        visited.add(beginNode);

        while (!q.isEmpty()) {
            DFANode n = q.remove();
            for (Entry<Character, DFANode> entry : n.getEdges().entrySet()) {
                if (visited.contains(entry.getValue()))
                    continue;
                q.add(entry.getValue());
                visited.add(entry.getValue());
            }
        }

        return new ArrayList<DFANode>(visited);
    }

    // FOR DEBUG ONLY
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Set<DFANode> visited = new HashSet<DFANode>();
        Queue<DFANode> q = new ArrayDeque<DFANode>();
        q.add(beginNode);

        while (!q.isEmpty()) {
            DFANode n = q.remove();
            builder.append(n.toString());
            builder.append('\n');

            for (Entry<Character, DFANode> entry : n.getEdges().entrySet()) {
                builder.append("  " + (entry.getKey()) + "--> " + entry.getValue());
                builder.append('\n');
                if (!visited.contains(entry.getValue())) {
                    visited.add(entry.getValue());
                    q.add(entry.getValue());
                }
            }
        }

        return builder.toString();
    }
}
