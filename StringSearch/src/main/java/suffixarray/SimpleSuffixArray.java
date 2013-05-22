package suffixarray;

import java.util.Arrays;

public class SimpleSuffixArray extends SuffixArray {
    public SimpleSuffixArray(String text) {
        super(text, new SuffixArraySorter() {
            @Override
            public void apply(String[] ss) {
                Arrays.sort(ss);
            }
        });
    }
}
