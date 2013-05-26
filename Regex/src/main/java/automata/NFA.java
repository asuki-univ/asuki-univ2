package automata;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class NFA {
    private NFANode beginNode;
    private NFANode endNode;

    public NFA(NFANode beginNode, NFANode endNode) {
        this.beginNode = beginNode;
        this.endNode = endNode;
    }

    public NFANode getBeginNode() {
        return beginNode;
    }

    public NFANode getEndNode() {
        return endNode;
    }

    // FOR DEBUGGING ONLY!
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        Set<NFANode> visited = new HashSet<NFANode>();
        Queue<NFANode> q = new ArrayDeque<NFANode>();
        q.add(beginNode);

        while (!q.isEmpty()) {
            NFANode n = q.remove();
            builder.append(n.toString());
            builder.append('\n');

            for (NFAEdge edge : n.getEdges()) {
                builder.append("  " + (edge.getLabel().isEmpty() ? "null" : edge.getLabel().getChar()) + "--> " + edge.getDestination());
                builder.append('\n');
                if (!visited.contains(edge.getDestination())) {
                    visited.add(edge.getDestination());
                    q.add(edge.getDestination());
                }
            }
        }

        return builder.toString();
    }

    public boolean accepts(String s) {
        Set<NFANode> nodes = new HashSet<NFANode>();
        nodes.add(beginNode);

        for (int i = 0; i < s.length(); ++i) {
            expandEmptyLabel(nodes);
            Set<NFANode> newNodes = new HashSet<NFANode>();
            for (NFANode node : nodes) {
                for (NFAEdge edge : node.getEdges()) {
                    if (edge.getLabel().accepts(s.charAt(i))) {
                        newNodes.add(edge.getDestination());
                    }
                }
            }

            nodes = newNodes;
        }

        expandEmptyLabel(nodes);
        for (NFANode node : nodes) {
            if (node.isFinal())
                return true;
        }
        return false;
    }

    private static void expandEmptyLabel(Set<NFANode> nodes) {
        Queue<NFANode> q = new ArrayDeque<NFANode>(nodes);
        while (!q.isEmpty()) {
            NFANode node = q.remove();
            for (NFAEdge edge : node.getEdges()) {
                if (!edge.getLabel().isEmpty())
                    continue;
                if (nodes.contains(edge.getDestination()))
                    continue;
                q.add(edge.getDestination());
                nodes.add(edge.getDestination());
            }
        }
    }
}
