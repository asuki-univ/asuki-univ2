package stringsearch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public abstract class AbstractStringSearchTest {

    protected abstract AbstractStringSearch searcher();
    
    @Test
    public void findEasyPattern() {
        assertThat(searcher().search("abc", "abcde"), is(0));
        assertThat(searcher().search("cde", "abcde"), is(2));
    }
    
    @Test
    public void findFromEmptyText() {
        assertThat(searcher().search("abc", ""), is(0));
    }
    
    // TODO(mayah): JUnit must have a good way to handle exception.
    @Test
    public void findUsingEmptyPattern() {
        try {
            searcher().search("", "hogehoge");
        } catch (IllegalArgumentException e) {
            // OK.
        }
    }
}
