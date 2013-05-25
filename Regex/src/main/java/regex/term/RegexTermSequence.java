package regex.term;


public class RegexTermSequence extends RegexTerm {
    private RegexTerm leftTerm;
    private RegexTerm rightTerm;

    public RegexTermSequence(RegexTerm leftTerm, RegexTerm rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public TermType getType() {
        return TermType.SEQUENCE;
    }

    public RegexTerm getLeftTerm() {
        return leftTerm;
    }

    public RegexTerm getRightTerm() {
        return rightTerm;
    }
}