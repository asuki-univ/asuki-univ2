package suffixarray;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import suffixarray.SuffixArray.SuffixArrayRange;
import suffixarray.impl.SimpleSuffixArray;

public abstract class SuffixArrayTest {

    public abstract SuffixArray makeSuffixArray(String text);

    @Test
    public void testMakeSuffixArray() {
        String s = "AABBCCDDEEFFGG";

        String[] expected = new String[s.length()];
        for (int i = 0; i < s.length(); ++i)
            expected[i] = s.substring(i);
        Arrays.sort(expected);

        SimpleSuffixArray sa = new SimpleSuffixArray(s);

        assertThat(sa.size(), is(s.length()));
        for (int i = 0; i < s.length(); ++i) {
            assertThat(sa.get(i).suffix, equalTo(expected[i]));
        }

        assertThat(sa.get(sa.find("AA").beginIndex).index, equalTo(0));
        assertThat(sa.get(sa.find("BB").beginIndex).index, equalTo(2));

        ArrayList<Integer> c = new ArrayList<Integer>();
        SuffixArrayRange range = sa.find("C");
        for (int i = range.beginIndex; i < range.endIndex; ++i)
            c.add(sa.get(i).index);
        Collections.sort(c);
        assertThat(c.toArray(new Integer[0]), equalTo(new Integer[] { 4, 5 }));
    }
}
