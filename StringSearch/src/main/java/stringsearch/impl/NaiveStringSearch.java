package stringsearch.impl;

import stringsearch.StringSearcher;

public class NaiveStringSearch implements StringSearcher {
    @Override
    public int search(String pattern, String text) {
        for (int m = 0; m + pattern.length() <= text.length(); ++m) {
            if (pattern.equals(text.substring(m, m + pattern.length())))
                return m;
        }

        // 見つからなかった
        return text.length();
    }
}
