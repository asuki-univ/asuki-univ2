package regex;

import regex.term.RegexTermOptional;
import regex.term.RegexTermSelection;
import regex.term.RegexTermSequence;
import regex.term.RegexTermStar;
import regex.term.RegexTermCharacter;
import regex.term.RegexTerm;


public class RegexParser {
    private static final char[] META_CHARACTERS = new char[] {
        '(', ')', '*', '?', '|'
    };

    private String regex;
    private int pos;

    public RegexParser(String regex) {
        this.regex = regex;
        this.pos = 0;
    }

    private static boolean isMetaCharacter(char c) {
        for (char meta : META_CHARACTERS) {
            if (c == meta)
                return true;
        }

        return false;
    }

    public RegexTerm parse() {
        RegexTerm node = parseRegex();
        if (pos != regex.length())
            return null;

        return node;
    }

    private RegexTerm parseRegex() {
        if (regex.length() <= pos)
            return null;

        RegexTerm r1 = parseSeq();
        if (regex.length() <= pos || regex.charAt(pos) != '|')
            return r1;

        ++pos;
        RegexTerm r2 = parseRegex();
        return new RegexTermSelection(r1, r2);
    }

    private RegexTerm parseSeq() {
        if (regex.length() <= pos)
            return null;

        RegexTerm r1 = parseAdditional();
        if (r1 == null)
            return null;

        RegexTerm r2 = parseSeq();
        if (r2 == null)
            return r1;
        return new RegexTermSequence(r1, r2);
    }

    private RegexTerm parseAdditional() {
        if (regex.length() <= pos)
            return null;

        RegexTerm simple = parseSimple();
        if (regex.length() <= pos)
            return simple;
        if (regex.charAt(pos) == '?') {
            ++pos;
            return new RegexTermOptional(simple);
        } else if (regex.charAt(pos) == '*') {
            ++pos;
            return new RegexTermStar(simple);
        } else {
            return simple;
        }
    }

    private RegexTerm parseSimple() {
        if (regex.length() <= pos)
            return null;

        char c = regex.charAt(pos);
        if (c == '(') {
            ++pos;
            RegexTerm node = parseRegex();
            if (node == null)
                throw new IllegalArgumentException();
            if (regex.charAt(pos++) != ')')
                throw new IllegalArgumentException();
            return node;
        } else if (isMetaCharacter(c)) {
            return null;
        } else {
            ++pos;
            return new RegexTermCharacter(c);
        }
    }
}
