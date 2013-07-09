package regex.term;


public class RegexTermAlnum extends RegexTerm {
    private char c;

    public RegexTermAlnum(char c) {
        this.c = c;
    }

    @Override
    public TermType getType() {
        return TermType.CHAR;
    }

    public char getChar() {
        return c;
    }
}