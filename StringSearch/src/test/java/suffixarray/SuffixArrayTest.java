package suffixarray;

import java.util.Arrays;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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
            assertThat(sa.get(i), equalTo(expected[i]));
        }
    }
}
