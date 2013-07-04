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

    public boolean accepts(String s) {
        Set<NFANode> nodes = new HashSet<NFANode>();
        nodes.add(beginNode);

        for (int i = 0; i < s.length(); ++i) {
            expandEpsilonEdgeDestinations(nodes);
            Set<NFANode> newNodes = new HashSet<NFANode>();
            for (NFANode node : nodes)
                newNodes.addAll(node.getDestinations(s.charAt(i)));

            nodes = newNodes;
        }

        expandEpsilonEdgeDestinations(nodes);
        for (NFANode node : nodes) {
            if (node.isFinal())
                return true;
        }
        return false;
    }

    private static void expandEpsilonEdgeDestinations(Set<NFANode> nodes) {
        Queue<NFANode> q = new ArrayDeque<NFANode>(nodes);
        while (!q.isEmpty()) {
            NFANode node = q.remove();
            for (NFANode dest : node.getEpsilonEdgeDestinations()) {
                if (nodes.contains(dest))
                    continue;
                q.add(dest);
                nodes.add(dest);
            }
        }
    }
}
