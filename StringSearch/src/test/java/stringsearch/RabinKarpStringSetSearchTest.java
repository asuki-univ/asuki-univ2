package stringsearch;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RabinKarpStringSetSearchTest {
    protected RabinKarpStringSetSearch seracher() {
        return new RabinKarpStringSetSearch();
    }

    private int search(String text, String[] patterns) {
        RabinKarpStringSetSearch searcher = new RabinKarpStringSetSearch();

        Set<String> patternSet = new HashSet<String>();
        for (String pattern : patterns)
            patternSet.add(pattern);

        return searcher.search(patternSet, text);
    }

    @Test
    public void findFromEmptyText() {
        assertThat(search("abcabcabcabc", new String[] { "ca", "cc" }), is(2));
        assertThat(search("abcdexyz", new String[] { "aaa", "xyz" }), is(5));
        assertThat(search("abcdefghijk", new String[] { "abc", "def" }), is(0));
        assertThat(search("hogehoge", new String[] { "fuga", "piyo" }), is(8));
    }
}
