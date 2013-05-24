package regex;

public class NFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        return new RegexParser(regex).parse().createAutomata().accepts(s);
    }
}
