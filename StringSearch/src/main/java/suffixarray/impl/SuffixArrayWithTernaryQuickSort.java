package suffixarray.impl;

import java.util.Comparator;

import suffixarray.SuffixArray;
import suffixarray.SuffixArrayItem;
import suffixarray.SuffixArraySorter;
import suffixarray.TernaryQuickSort;

//public class SuffixArrayWithTernaryQuickSort extends SuffixArray {
//    public SuffixArrayWithTernaryQuickSort(String text) {
//        super(text, new SuffixArraySorter() {
//            @Override
//            public void apply(SuffixArrayItem[] items) {
//                TernaryQuickSort.sort(items);
//            }
//        });
//    }
//}
