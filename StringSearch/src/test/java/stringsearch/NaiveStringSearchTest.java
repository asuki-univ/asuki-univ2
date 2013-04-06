package stringsearch;

public class NaiveStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected AbstractStringSearch searcher() {
        return new NaiveStringSearch();
    }
}
