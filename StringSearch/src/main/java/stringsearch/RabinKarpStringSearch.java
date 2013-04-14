package stringsearch;

public class RabinKarpStringSearch implements StringSearcher {
    @Override
    public int search(String pattern, String text) {
        if (pattern == null || "".equals(pattern))
            throw new IllegalArgumentException();

        if (text.length() < pattern.length())
            return text.length();

        int patternHash = hash(pattern);
        int textHash = hash(text.substring(0, pattern.length()));

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

    private int hash(String word) {
        int result = 0;
        for (int i = 0; i < word.length(); ++i)
            result += word.charAt(i);

        return result;
    }
}
