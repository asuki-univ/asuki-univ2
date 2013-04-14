package stringsearch;

public class NaiveStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new NaiveStringSearch();
    }
}
