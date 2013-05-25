package regex;

import automata.NFA;
import automata.NFABuilder;
import regex.RegexParser;

public class NFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        NFA nfa = new NFABuilder().build(new RegexParser(regex).parse());
        return nfa.accepts(s);
    }
}
