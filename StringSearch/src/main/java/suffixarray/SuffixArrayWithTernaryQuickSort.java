package suffixarray;


public class SuffixArrayWithTernaryQuickSort extends SuffixArray {
    public SuffixArrayWithTernaryQuickSort(String text) {
        super(text, new SuffixArraySorter() {
            @Override
            public void apply(String[] ss) {
                TernaryQuickSort.sort(ss);
            }
        });
    }
}
