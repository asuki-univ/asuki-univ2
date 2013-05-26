package regex.term;


public class RegexTermOption extends RegexTerm {
    private RegexTerm term;

    public RegexTermOption(RegexTerm term) {
        this.term = term;
    }

    @Override
    public TermType getType() {
        return TermType.OPTION;
    }

    public RegexTerm getTerm() {
        return term;
    }
}