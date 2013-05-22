package suffixarray;

public class SuffixArray {
    private final String[] suffixes;

    public SuffixArray(String text, SuffixArraySorter sorter) {
        suffixes = new String[text.length()];
        for (int i = 0; i < text.length(); ++i)
            suffixes[i] = text.substring(i);

        sorter.apply(suffixes);
    }

    public int size() {
        return suffixes.length;
    }

    public String get(int ith) {
        return suffixes[ith];
    }
}
