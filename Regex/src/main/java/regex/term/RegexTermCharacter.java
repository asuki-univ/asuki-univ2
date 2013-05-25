package regex.term;


public class RegexTermCharacter extends RegexTerm {
    private char c;

    public RegexTermCharacter(char c) {
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