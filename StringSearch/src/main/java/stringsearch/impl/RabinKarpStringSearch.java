package stringsearch.impl;

import stringsearch.StringSearcher;

public class RabinKarpStringSearch implements StringSearcher {

    @Override
    public int search(String pattern, String text) {
        if (text.length() < pattern.length())
            return text.length();

        // textの長さの substring を取って、ハッシュを作成しておく
        int patternHash = hash(pattern);
        int textHash = hash(text.substring(0, pattern.length()));

        // ハッシュが異なれば equals() は必ず false を返すので、まずハッシュをチェック。ハッシュが一致すれば、実際にチェックする
        int i = 0;
        while (true) {
            if (patternHash == textHash && pattern.equals(text.substring(i, i + pattern.length())))
                return i;

            if (text.length() <= i + pattern.length())
                return text.length();

            textHash = textHash - text.charAt(i) + text.charAt(i + pattern.length());
            ++i;
        }
    }

    // word のハッシュ値を求める。
    private int hash(String word) {
        int result = 0;
        for (int i = 0; i < word.length(); ++i)
            result += word.charAt(i);

        return result;
    }
}
