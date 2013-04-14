package stringsearch;

public class RabinKarpStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new RabinKarpStringSearch();
    }
}
