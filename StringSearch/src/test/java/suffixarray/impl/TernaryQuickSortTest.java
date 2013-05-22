package suffixarray.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import suffixarray.TernaryQuickSort;

public class TernaryQuickSortTest {

    @Test
    public void testEmpty() {
        String[] ss = new String[0];
        TernaryQuickSort.sort(ss);

        assertThat(ss, equalTo(new String[0]));
    }

    @Test
    public void testSingleton() {
        String[] ss = new String[] { "a" };
        TernaryQuickSort.sort(ss);

        assertThat(ss, equalTo(new String[] { "a" }));
    }

    @Test
    public void testSimple1() {
        String[] actual = new String[] {
                "a", "b", "c"
        };
        String[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSimple2() {
        String[] actual = new String[] {
                "c", "b", "a"
        };
        String[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix1() {
        String[] actual = new String[] {
                "aaaaa", "aaaa", "aaa"
        };
        String[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix2() {
        String[] actual = new String[] {
                "aaaaa", "aaaab", "aaabb"
        };
        String[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testSamePrefix3() {
        String[] actual = new String[] {
                "ab", "ab", "ab"
        };
        String[] expected = actual.clone();

        Arrays.sort(expected);
        TernaryQuickSort.sort(actual);

        assertThat(actual, equalTo(expected));
    }
}
