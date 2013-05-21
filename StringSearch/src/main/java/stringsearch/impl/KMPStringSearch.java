package stringsearch.impl;

import stringsearch.StringSearcher;

public class KMPStringSearch implements StringSearcher {

    @Override
    public int search(String pattern, String text) {
        int[] table = makeTable(pattern);

        int i = 0;
        int m = 0;

        while (i + m < text.length()) {
            if (pattern.charAt(m) == text.charAt(i + m)) {
                m = m + 1;
                if (m == pattern.length())
                    return i;
            } else {
                i = i + m - table[m];
                if (m > 0)
                    m = table[m];
            }
        }

        // 見つからなかった
        return text.length();
    }

    static int[] makeTable(String pattern) {
        assert(pattern.length() >= 1);

        if (pattern.length() <= 1)
            return new int[] { -1 };

        int[] T = new int[pattern.length()];
        T[0] = -1;
        T[1] = 0;

        int m = 2;
        int k = 0; // k 文字目まで接頭辞

        while (m < pattern.length()) {
            if (pattern.charAt(m - 1) == pattern.charAt(k)) {
                T[m++] = ++k;
            } else if (k > 0) {
                k = T[k];
            } else {
                T[m++] = 0;
            }
        }

        return T;
    }
}
