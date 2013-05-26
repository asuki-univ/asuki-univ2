package regex;

import automata.DFA;
import automata.NFA;
import automata.NFAConverter;
import automata.NFABuilder;
import regex.RegexParser;

public class DFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        NFA nfa = new NFABuilder().build(new RegexParser(regex).parse());
        DFA dfa = NFAConverter.convert(nfa);
        return dfa.accepts(s);
    }
}
