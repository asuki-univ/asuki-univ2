package automata;

public class Label {
    private char c;

    static public Label newEmptyLabel() {
        return new Label('\0');
    }

    static public Label newCharLabel(char c) {
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

