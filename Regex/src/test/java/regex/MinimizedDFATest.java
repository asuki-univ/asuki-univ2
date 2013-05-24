package regex;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MinimizedDFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        return new RegexParser(regex).parse().createAutomata().convertToDFA().minimizeDFA().accepts(s);
    }

    private DFA makeDFA(String regex) {
        return new RegexParser(regex).parse().createAutomata().convertToDFA().minimizeDFA();
    }

    @Test
    public void testSize() {
        assertThat(makeDFA("a").getNodeSize(), is(2));
        assertThat(makeDFA("aa").getNodeSize(), is(3));
        assertThat(makeDFA("a*").getNodeSize(), is(1));
        assertThat(makeDFA("aa*").getNodeSize(), is(2));
        assertThat(makeDFA("a*b").getNodeSize(), is(2));
    }
}
