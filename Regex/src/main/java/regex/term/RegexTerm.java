package regex.term;


public abstract class RegexTerm {
    public enum TermType {
        ALNUM,
        OPTION,
        SELECTION,
        SEQUENCE,
        STAR
    }

    public abstract TermType getType();
}