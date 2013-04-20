package stringsearch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public abstract class AbstractStringSearchTest {
    protected abstract StringSearcher searcher();

    @Test
    public void findFromEmptyText() {
        assertThat(searcher().search("abc", ""), is(0));
        assertThat(searcher().search("a", ""), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findUsingEmptyPattern() {
        searcher().search("", "should throw exception");
    }

    @Test
    public void patternIsLongerThanText() {
        assertThat(searcher().search("abcdef", "abcde"), is(5));
        assertThat(searcher().search("uvwxyz", "abcde"), is(5));
    }

    @Test
    public void findWithSingleCharacter() {
        assertThat(searcher().search("a", "abcde"), is(0));
        assertThat(searcher().search("b", "abcde"), is(1));
        assertThat(searcher().search("c", "abcde"), is(2));
        assertThat(searcher().search("d", "abcde"), is(3));
        assertThat(searcher().search("e", "abcde"), is(4));
        assertThat(searcher().search("f", "abcde"), is(5));
    }

    @Test
    public void findWithSubstring() {
        assertThat(searcher().search("abc", "abcde"), is(0));
        assertThat(searcher().search("bcd", "abcde"), is(1));
        assertThat(searcher().search("cde", "abcde"), is(2));
        assertThat(searcher().search("def", "abcde"), is(5));
        assertThat(searcher().search("abf", "abcde"), is(5));
    }

    @Test
    public void findMediumPattern() {
        assertThat(searcher().search("ababababababab", "abababababababababababababababababab"), is(0));
        assertThat(searcher().search("aecdeabcd", "abcdeabcaecdeabcd"), is(8));
        assertThat(searcher().search("abcdeabcd", "abcdeabcabcdeabcd"), is(8));
    }
}
