package suffixarray.impl;

import suffixarray.TernaryQuickSort;

// TODO(mayah): SimpleSuffixArray を継承するのは間違っているような気がする
public class SuffixArrayWithTernaryQuickSort extends SimpleSuffixArray {
    public SuffixArrayWithTernaryQuickSort(String text) {
        super(text);
    }

    @Override
    protected void sort() {
        TernaryQuickSort.sort(data);
    }
}
