package regex;

import regex.term.RegexTermOption;
import regex.term.RegexTermSelection;
import regex.term.RegexTermSequence;
import regex.term.RegexTermStar;
import regex.term.RegexTermAlnum;
import regex.term.RegexTerm;

public class RegexParser {
    class ParseException extends Exception {}

    private String regex;
    private int pos;

    public RegexParser(String regex) {
        this.regex = regex;
        this.pos = 0;
    }

    private static boolean isAlnum(char c) {
        return ('0' <= c && c <= '9') || ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    public RegexTerm parse() {
        pos = 0;

        try {
            RegexTerm term = parseRegex();
            if (pos != regex.length())
                return null;
            return term;
        } catch (ParseException e) {
            return null;
        }
    }

    private RegexTerm parseRegex() throws ParseException {
        if (regex.length() <= pos)
            return null;

        RegexTerm leftTerm = parseSeq();
        if (leftTerm == null)
            return null;
        if (regex.length() <= pos || regex.charAt(pos) != '|')
            return leftTerm;

        ++pos;
        RegexTerm rightTerm = parseRegex();
        if (rightTerm == null)
            return null;
        return new RegexTermSelection(leftTerm, rightTerm);
    }

    private RegexTerm parseSeq() throws ParseException {
        if (regex.length() <= pos)
            return null;

        RegexTerm leftTerm = parseAdditional();
        if (leftTerm == null)
            return null;

        RegexTerm rightTerm = parseSeq();
        if (rightTerm == null)
            return leftTerm;

        return new RegexTermSequence(leftTerm, rightTerm);
    }

    private RegexTerm parseAdditional() throws ParseException {
        if (regex.length() <= pos)
            return null;

        RegexTerm simple = parseSimple();
        if (simple == null)
            return null;

        if (regex.length() <= pos)
            return simple;

        char c = regex.charAt(pos);
        switch (c) {
        case '?':
            ++pos;
            return new RegexTermOption(simple);
        case '*':
            ++pos;
            return new RegexTermStar(simple);
        default:
            return simple;
        }
    }

    private RegexTerm parseSimple() throws ParseException {
        if (regex.length() <= pos)
            return null;

        char c = regex.charAt(pos);
        if (c == '(') {
            ++pos;
            RegexTerm node = parseRegex();
            if (node == null)
                throw new ParseException();
            if (regex.charAt(pos++) != ')')
                throw new ParseException();
            return node;
        } if (isAlnum(c)) {
            ++pos;
            return new RegexTermAlnum(c);
        } else {
            return null;
        }
    }
}
