package suffixarray.impl;

import suffixarray.SuffixArray;
import suffixarray.SuffixArrayTest;

public class SimpleSuffixArrayTest extends SuffixArrayTest {
    @Override
    public SuffixArray makeSuffixArray(String text) {
        return new SimpleSuffixArray(text);
    }
}
