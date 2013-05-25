package regex;

import automata.DFA;
import automata.NFA;
import automata.NFABuilder;
import regex.RegexParser;

public class DFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        NFA nfa = new NFABuilder().build(new RegexParser(regex).parse());
        DFA dfa = nfa.convertToDFA();
        return dfa.accepts(s);
    }
}
