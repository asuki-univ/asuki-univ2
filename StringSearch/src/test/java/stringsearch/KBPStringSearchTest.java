package stringsearch;

public class KBPStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected AbstractStringSearch searcher() {
        return new KMPStringSearch();
    }
}
