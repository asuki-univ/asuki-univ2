package regex.term;


public class RegexTermStar extends RegexTerm {
    private RegexTerm term;

    public RegexTermStar(RegexTerm term) {
        this.term = term;
    }

    @Override
    public TermType getType() {
        return TermType.STAR;
    }

    public RegexTerm getTerm() {
        return term;
    }
}