package automata;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
                for (Entry<Character, List<NFANode>> e : n.getEdges().entrySet()) {
                    Set<NFANode> nodes = labelToPowerset.get(e.getKey());
                    if (nodes == null) {
                        nodes = new HashSet<NFANode>();
                        labelToPowerset.put(e.getKey(), nodes);
                    }

                    for (NFANode dest : e.getValue())
                        nodes.addAll(getNodesWithEpsilonTransition(dest));
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
        Queue<NFANode> q = new ArrayDeque<NFANode>();

        visited.add(node);
        q.add(node);

        while (!q.isEmpty()) {
            NFANode n = q.remove();
            for (NFANode dest : n.getEpsilonEdgeDestinations()) {
                if (visited.contains(dest))
                    continue;

                q.add(dest);
                visited.add(dest);
            }
        }

        return visited;
    }
}
