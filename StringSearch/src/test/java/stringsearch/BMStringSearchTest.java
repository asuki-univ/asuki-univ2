package stringsearch;

public class BMStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new BMStringSearch();
    }
}
