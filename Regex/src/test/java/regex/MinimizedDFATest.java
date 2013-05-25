package regex;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import automata.DFA;
import automata.NFA;
import automata.NFABuilder;


import regex.RegexParser;

public class MinimizedDFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        NFA nfa = new NFABuilder().build(new RegexParser(regex).parse());
        DFA dfa = nfa.convertToDFA();
        DFA minimized = dfa.minimizeDFA();
        return minimized.accepts(s);
    }

    @Test
    public void testSize() {
        assertThat(makeDFA("a").getNodeSize(), is(2));
        assertThat(makeDFA("aa").getNodeSize(), is(3));
        assertThat(makeDFA("a*").getNodeSize(), is(1));
        assertThat(makeDFA("aa*").getNodeSize(), is(2));
        assertThat(makeDFA("a*b").getNodeSize(), is(2));
    }

    private static DFA makeDFA(String regex) {
        NFA nfa = new NFABuilder().build(new RegexParser(regex).parse());
        DFA dfa = nfa.convertToDFA();
        DFA minimized = dfa.minimizeDFA();
        return minimized;
    }
}
