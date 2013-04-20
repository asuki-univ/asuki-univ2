package regex;

import org.junit.Test;

public class MinimizedDFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        return new RegexParser(regex).parse().createAutomata().convertToDFA().minimizeDFA().accepts(s);
    }
    
    @Test
    public void testSize() {
        DFA dfa = new RegexParser("aa").parse().createAutomata().convertToDFA();
        System.out.println(dfa.toString());
        DFA min = dfa.minimizeDFA();
    }
}
