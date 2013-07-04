package automata;

import regex.term.RegexTermCharacter;
import regex.term.RegexTerm;
import regex.term.RegexTermOption;
import regex.term.RegexTermSelection;
import regex.term.RegexTermSequence;
import regex.term.RegexTermStar;

public class NFABuilder {
    public NFA build(RegexTerm term) {
        switch (term.getType()) {
        case CHAR:
            return buildFromChar((RegexTermCharacter) term);
        case OPTION:
            return buildFromOption((RegexTermOption) term);
        case SELECTION:
            return buildFromSelection((RegexTermSelection) term);
        case SEQUENCE:
            return buildFromSequence((RegexTermSequence) term);
        case STAR:
            return buildFromStar((RegexTermStar) term);
        }

        assert(false);
        throw new RuntimeException("Unknown RegexTerm");
    }

    private NFA buildFromChar(RegexTermCharacter term) {
        NFANode beginNode = new NFANode(false);
        NFANode endNode = new NFANode(true);
        beginNode.addEdge(term.getChar(), endNode);

        return new NFA(beginNode, endNode);
    }

    public NFA buildFromOption(RegexTermOption term) {
        NFA am = build(term.getTerm());
        am.getBeginNode().addEpsilonEdge(am.getEndNode());

        return am;
    }

    public NFA buildFromSelection(RegexTermSelection term) {
        NFA am1 = build(term.getLeftTerm());
        NFA am2 = build(term.getRightTerm());

        NFANode beginNode = new NFANode(false);
        NFANode endNode = new NFANode(true);

        am1.getEndNode().setFinal(false);
        am2.getEndNode().setFinal(false);

        beginNode.addEpsilonEdge(am1.getBeginNode());
        beginNode.addEpsilonEdge(am2.getBeginNode());
        am1.getEndNode().addEpsilonEdge(endNode);
        am2.getEndNode().addEpsilonEdge(endNode);

        return new NFA(beginNode, endNode);
    }

    public NFA buildFromSequence(RegexTermSequence term) {
        NFA am1 = build(term.getLeftTerm());
        NFA am2 = build(term.getRightTerm());

        am1.getEndNode().setFinal(false);
        am1.getEndNode().addEpsilonEdge(am2.getBeginNode());

        return new NFA(am1.getBeginNode(), am2.getEndNode());
    }

    public NFA buildFromStar(RegexTermStar term) {
        NFA am = build(term.getTerm());
        am.getBeginNode().addEpsilonEdge(am.getEndNode());
        am.getEndNode().addEpsilonEdge(am.getBeginNode());

        return am;
    }
}
