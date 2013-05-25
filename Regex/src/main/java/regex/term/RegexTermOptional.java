package regex.term;


public class RegexTermOptional extends RegexTerm {
    private RegexTerm term;

    public RegexTermOptional(RegexTerm term) {
        this.term = term;
    }

    @Override
    public TermType getType() {
        return TermType.OPTIONAL;
    }

    public RegexTerm getTerm() {
        return term;
    }
}