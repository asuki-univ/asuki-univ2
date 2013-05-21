package stringsearch.impl;

import stringsearch.AbstractStringSearchTest;
import stringsearch.StringSearcher;
import stringsearch.StringSearcherWrapper;
import stringsearch.impl.RabinKarpStringSearch;

public class RabinKarpStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new StringSearcherWrapper(new RabinKarpStringSearch());
    }
}
