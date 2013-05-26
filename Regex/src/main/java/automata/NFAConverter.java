package automata;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;

public class NFAConverter {

    public static DFA convert(NFA nfa) {
        Map<Set<NFANode>, DFANode> powersetMap = new HashMap<Set<NFANode>, DFANode>();
        Set<DFANode> visited = new HashSet<DFANode>();

        Set<NFANode> beginPowerNode = getNodesWithEpsilonTransition(nfa.getBeginNode());
        DFANode newBeginNode = new DFANode(false);
        powersetMap.put(beginPowerNode, newBeginNode);

        Queue<Set<NFANode>> q = new ArrayDeque<Set<NFANode>>();
        q.add(beginPowerNode);

        while (!q.isEmpty()) {
            // 各labelで、powersetを取得したい。
            Set<NFANode> powerset = q.remove();
            DFANode newNode = powersetMap.get(powerset);
            assert (newNode != null);

            visited.add(newNode);

            Map<Character, Set<NFANode>> labelToPowerset = new HashMap<Character, Set<NFANode>>();
            for (NFANode n : powerset) {
                for (NFAEdge e : n.getEdges()) {
                    if (e.getLabel().isEmpty())
                        continue;

                    if (labelToPowerset.containsKey(e.getLabel().getChar())) {
                        Set<NFANode> nodes = labelToPowerset.get(e.getLabel().getChar());
                        nodes.addAll(getNodesWithEpsilonTransition(e.getDestination()));
                    } else {
                        Set<NFANode> nodes = new HashSet<NFANode>();
                        nodes.addAll(getNodesWithEpsilonTransition(e.getDestination()));
                        labelToPowerset.put(e.getLabel().getChar(), nodes);
                    }
                }
            }

            for (Entry<Character, Set<NFANode>> entry : labelToPowerset.entrySet()) {
                if (powersetMap.containsKey(entry.getValue())) {
                    DFANode n = powersetMap.get(entry.getValue());
                    newNode.addEdge(entry.getKey(), n);
                    if (!visited.contains(n))
                        q.add(entry.getValue());
                } else {
                    DFANode n = new DFANode(false);
                    powersetMap.put(entry.getValue(), n);
                    q.add(entry.getValue());
                    newNode.addEdge(entry.getKey(), n);
                }
            }
        }

        for (Entry<Set<NFANode>, DFANode> entry : powersetMap.entrySet()) {
            for (NFANode n : entry.getKey()) {
                if (n.isFinal()) {
                    entry.getValue().setFinal(true);
                    break;
                }
            }
        }

        return new DFA(newBeginNode);
    }

    // node からイプシロン遷移できるノードの集合を返す。自身も含む。
    private static Set<NFANode> getNodesWithEpsilonTransition(NFANode node) {
        Set<NFANode> visited = new HashSet<NFANode>();
        visited.add(node);
        Queue<NFANode> q = new ArrayDeque<NFANode>();
        q.add(node);

        while (!q.isEmpty()) {
            NFANode n = q.remove();
            for (NFAEdge edge : n.getEdges()) {
                if (!edge.getLabel().isEmpty())
                    continue;
                if (visited.contains(edge.getDestination()))
                    continue;
                q.add(edge.getDestination());
                visited.add(edge.getDestination());
            }
        }

        return visited;
    }
}
