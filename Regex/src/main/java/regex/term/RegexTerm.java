package regex.term;


public abstract class RegexTerm {
    public enum TermType {
        CHAR,
        OPTION,
        SELECTION,
        SEQUENCE,
        STAR
    }

    public abstract TermType getType();
}