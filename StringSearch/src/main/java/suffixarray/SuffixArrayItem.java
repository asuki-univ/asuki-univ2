package suffixarray;

public class SuffixArrayItem implements Comparable<SuffixArrayItem> {
    public final int index;
    public final String suffix;

    public SuffixArrayItem(int index, String suffix) {
        this.index = index;
        this.suffix = suffix;
    }

    @Override
    public int hashCode() {
        return suffix.hashCode() * 37 + index;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SuffixArrayItem))
            return false;

        SuffixArrayItem lhs = this;
        SuffixArrayItem rhs = (SuffixArrayItem) obj;

        return lhs.index == rhs.index && lhs.suffix.equals(rhs.suffix);
    }


    @Override
    public int compareTo(SuffixArrayItem rhs) {
        SuffixArrayItem lhs = this;

        int r = lhs.suffix.compareTo(rhs.suffix);
        if (r != 0)
            return r;

        return lhs.index - rhs.index;
    }
}