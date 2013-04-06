package stringsearch;

public class RabinKarpStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected AbstractStringSearch searcher() {
        return new RabinKarpStringSearch();
    }
}
