package stringsearch.impl;

import java.util.Arrays;

import stringsearch.StringSearcher;

public class BMStringSearch implements StringSearcher {
    final private int ALPHABETA_SIZE = 256;

    @Override
    public int search(String pattern, String text) {
        int[] delta1 = makeDelta1Table(pattern);
        int[] delta2 = makeDelta2Table(pattern);

        EXTERNAL: for (int i = pattern.length() - 1; i < text.length(); ) {
            for (int j = pattern.length() - 1; j >= 0; --i, --j) {
                if (text.charAt(i) != pattern.charAt(j)) {
                    i += Math.max(delta1[text.charAt(i)], delta2[j]);
                    continue EXTERNAL;
                }
            }

            // 見つからなかった
            return i + 1;
        }

        return text.length();
    }

    protected int[] makeDelta1Table(String pattern) {
        int[] table = new int[ALPHABETA_SIZE];
        Arrays.fill(table, pattern.length());

        for (int i = 0; i < pattern.length() - 1; ++i)
            table[pattern.charAt(i)] = pattern.length() - 1 - i;

        return table;
    }

    protected int[] makeDelta2Table(String pattern) {
        int[] delta2 = new int[pattern.length()];

        int lastPrefixIndex = pattern.length() - 1;
        for (int p = pattern.length() - 1; p >= 0; --p) {
            if (isPrefix(pattern, p + 1))
                lastPrefixIndex = p+1;
            delta2[p] = lastPrefixIndex + (pattern.length() - 1 - p);
        }

        for (int p = 0; p < pattern.length() - 1; p++) {
            int len = suffixLength(pattern, p);
            assert (pattern.charAt(p - len) != pattern.charAt(pattern.length() - 1 - len));
            //if (pattern.charAt(p - len) != pattern.charAt(patlen - 1 - len))
            delta2[pattern.length() - 1 - len] = pattern.length() - 1 - p + len;
        }

        return delta2;
    }

    // pattern の pos 番目から始まる文字列が、pattern の prefix になっていれば true
    // 例えば、isPrefix("123abc123", 6) == true
    boolean isPrefix(String pattern, int pos) {
        for (int i = 0; i < pattern.length() - pos; i++) {
            if (pattern.charAt(i) != pattern.charAt(pos + i))
                return false;
        }

        return true;
    }

    // pattern の pos 番目で終わる文字列が、pattern の suffix になっていれば、その長さを返す。
    // suffixLength("dddbcabc", 4) = 2
    int suffixLength(String pattern, int pos) {
        for (int i = 0; pos - i >= 0; ++i) {
            if (pattern.charAt(pos - i) != pattern.charAt(pattern.length() - 1 - i))
                return i;
        }
        return pos;
    }
}

