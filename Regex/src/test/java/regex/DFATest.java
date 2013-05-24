package regex;

public class DFATest extends AutomataTest {
    public boolean accepts(String regex, String s) {
        return new RegexParser(regex).parse().createAutomata().convertToDFA().accepts(s);
    }
}
