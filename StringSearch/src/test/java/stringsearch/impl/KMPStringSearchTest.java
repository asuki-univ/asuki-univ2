package stringsearch.impl;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Assert;
import org.junit.Test;

import stringsearch.AbstractStringSearchTest;
import stringsearch.StringSearcher;
import stringsearch.StringSearcherWrapper;

public class KMPStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new StringSearcherWrapper(new KMPStringSearch());
    }

    @Test
    public void testMakeTable() {
        Assert.assertThat(KMPStringSearch.makeTable("ABCABD"), equalTo(new int[] {
                -1, 0, 0, 0, 1, 2
        }));

        Assert.assertThat(KMPStringSearch.makeTable("ABABAABC"), equalTo(new int[] {
                -1, 0, 0, 1, 2, 3, 1, 2
        }));

        Assert.assertThat(KMPStringSearch.makeTable("ABABABAAB"), equalTo(new int[] {
                -1, 0, 0, 1, 2, 3, 4, 5, 1
        }));

        Assert.assertThat(KMPStringSearch.makeTable("AABAABAABC"), equalTo(new int[] {
                -1, 0, 1, 0, 1, 2, 3, 4, 5, 6
        }));

        Assert.assertThat(KMPStringSearch.makeTable("ABAABAD"), equalTo(new int[] {
                -1, 0, 0, 1, 1, 2, 3
        }));

        Assert.assertThat(KMPStringSearch.makeTable("AAAAAAAAAB"), equalTo(new int[] {
                -1, 0, 1, 2, 3, 4, 5, 6, 7, 8
        }));
    }
}
