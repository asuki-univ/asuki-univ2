package regex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

class NFANode {
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
    
    public DFA convertToDFA() {
        Map<Set<NFANode>, DFANode> powersetMap = new HashMap<Set<NFANode>, DFANode>();
        Set<DFANode> visited = new HashSet<DFANode>();
        
        Set<NFANode> beginPowerNode = getNodesWithEpsilonTransition(beginNode);
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
