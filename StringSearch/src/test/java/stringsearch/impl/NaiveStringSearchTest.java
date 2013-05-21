package stringsearch.impl;

import stringsearch.AbstractStringSearchTest;
import stringsearch.StringSearcher;
import stringsearch.StringSearcherWrapper;
import stringsearch.impl.NaiveStringSearch;

public class NaiveStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new StringSearcherWrapper(new NaiveStringSearch());
    }
}
