package regex;

public class Label {
    private char c;

    static Label newEmptyLabel() {
        return new Label('\0');
    }

    static Label newCharLabel(char c) {
        return new Label(c);
    }

    private Label(char c) {
        this.c = c;
    }

    public boolean isEmpty() {
        return c == '\0';
    }
    
    public boolean accepts(char c) {
        return this.c == c;
    }
    
    public char getChar() {
        return c;
    }
}

