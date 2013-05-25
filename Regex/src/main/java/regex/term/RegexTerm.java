package regex.term;


public abstract class RegexTerm {
    public enum TermType {
        CHAR,
        OPTIONAL,
        SELECTION,
        SEQUENCE,
        STAR
    }

    public abstract TermType getType();
}