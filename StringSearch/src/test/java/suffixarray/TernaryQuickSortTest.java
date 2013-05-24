package suffixarray;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import suffixarray.SuffixArrayItem;

public class TernaryQuickSortTest {

    @Test
    public void testEmpty() {
        SuffixArrayItem[] actual = new SuffixArrayItem[0];
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(new SuffixArrayItem[0]));
    }

    @Test
    public void testSingleton() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] { new SuffixArrayItem(0, "a") };
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(new SuffixArrayItem[] { new SuffixArrayItem(0, "a") }));
    }

    @Test
    public void testSimple1() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] {
                new SuffixArrayItem(0, "a"), new SuffixArrayItem(1, "b"), new SuffixArrayItem(2, "c")
        };
        SuffixArrayItem[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSimple2() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] {
                new SuffixArrayItem(0, "c"), new SuffixArrayItem(1, "b"), new SuffixArrayItem(2, "a")
        };
        SuffixArrayItem[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix1() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] {
                new SuffixArrayItem(0, "aaaaa"), new SuffixArrayItem(1, "aaaa"), new SuffixArrayItem(2, "aaa"),
                new SuffixArrayItem(3, "aa"), new SuffixArrayItem(4, "a"), new SuffixArrayItem(5, ""),
        };
        SuffixArrayItem[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix2() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] {
                new SuffixArrayItem(0, "aaaaa"), new SuffixArrayItem(1, "aaaab"), new SuffixArrayItem(2, "aaabb"),
        };
        SuffixArrayItem[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix3() {
        SuffixArrayItem[] actual = new SuffixArrayItem[] {
                new SuffixArrayItem(0, "ab"), new SuffixArrayItem(1, "ab"), new SuffixArrayItem(2, "ab"),
        };
        SuffixArrayItem[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }
}
