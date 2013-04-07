package stringsearch;

public class BMStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected AbstractStringSearch searcher() {
        return new BMStringSearch();
    }
}
