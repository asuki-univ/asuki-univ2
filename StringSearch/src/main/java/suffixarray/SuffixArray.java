package suffixarray;

public abstract class SuffixArray {
    protected final SuffixArrayItem[] data;

    public class SuffixArrayRange {
        public int beginIndex;
        public int endIndex;

        public SuffixArrayRange(int beginIndex, int endIndex) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }
    }

    public SuffixArray(String text) {
        data = new SuffixArrayItem[text.length()];
        for (int i = 0; i < text.length(); ++i)
            data[i] = new SuffixArrayItem(i, text.substring(i));

        sort();
    }

    public abstract SuffixArrayRange find(String s);
    protected abstract void sort();

    public int size() {
        return data.length;
    }

    public SuffixArrayItem get(int ith) {
        return data[ith];
    }
}
