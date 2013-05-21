package stringsearch;

public class StringSearcherWrapper implements StringSearcher {
    private StringSearcher searcher;

    public StringSearcherWrapper(StringSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public int search(String pattern, String text) {
        if (pattern == null || "".equals(pattern))
            throw new IllegalArgumentException();

        return searcher.search(pattern, text);
    }
}