package automata;

public class NFAEdge {
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