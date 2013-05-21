package stringsearch.impl;

import stringsearch.AbstractStringSearchTest;
import stringsearch.StringSearcher;
import stringsearch.StringSearcherWrapper;
import stringsearch.impl.BMStringSearch;

public class BMStringSearchTest extends AbstractStringSearchTest {
    @Override
    protected StringSearcher searcher() {
        return new StringSearcherWrapper(new BMStringSearch());
    }
}
