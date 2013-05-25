package regex.term;


public class RegexTermSelection extends RegexTerm {
    private RegexTerm leftTerm;
    private RegexTerm rightTerm;

    public RegexTermSelection(RegexTerm leftTerm, RegexTerm rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public TermType getType() {
        return TermType.SELECTION;
    }

    public RegexTerm getLeftTerm() {
        return leftTerm;
    }

    public RegexTerm getRightTerm() {
        return rightTerm;
    }
}