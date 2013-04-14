package stringsearch;

public class KBPStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new KMPStringSearch();
    }
}
