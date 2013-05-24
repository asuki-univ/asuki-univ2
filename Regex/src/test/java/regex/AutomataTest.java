package regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class AutomataTest {
    public abstract boolean accepts(String regex, String s);

    @Test
    public void testMatchSimple() {
        assertTrue(accepts("a", "a"));

        assertFalse(accepts("a", "b"));
    }

    @Test
    public void testMatchStar() {
        assertTrue(accepts("a*", ""));
        assertTrue(accepts("a*", "a"));
        assertTrue(accepts("a*", "aa"));
        assertTrue(accepts("a*", "aaaaa"));

        assertFalse(accepts("a*", "b"));
        assertFalse(accepts("a*", "ab"));
    }

    @Test
    public void testMatchOptional() {
        assertTrue(accepts("a?", ""));
        assertTrue(accepts("a?", "a"));

        assertFalse(accepts("a?", "aa"));
        assertFalse(accepts("a?", "aaa"));
        assertFalse(accepts("a?", "b"));
        assertFalse(accepts("a?", "ab"));
    }

    @Test
    public void testMatchSequence() {
        assertTrue(accepts("aa", "aa"));
        assertTrue(accepts("ab", "ab"));

        assertFalse(accepts("aa", ""));
        assertFalse(accepts("aa", "a"));
        assertFalse(accepts("aa", "ab"));
        assertFalse(accepts("aa", "aaa"));
        assertFalse(accepts("ab", "ba"));
        assertFalse(accepts("ab", "abb"));
        assertFalse(accepts("ab", "aba"));
    }

    @Test
    public void testMatchSelection() {
        assertTrue(accepts("a|b", "a"));
        assertTrue(accepts("a|b", "b"));
        assertTrue(accepts("a|ab", "a"));
        assertTrue(accepts("a|ab", "ab"));
        assertTrue(accepts("a|aa*", "aa"));
        assertTrue(accepts("(abc|xyz)", "xyz"));

        assertFalse(accepts("a|b", "c"));
        assertFalse(accepts("a|bc", "ac"));
        assertFalse(accepts("ab|ba", "aa"));
    }
}
