package stringsearch;

public class NaiveStringSearch implements StringSearcher {
    @Override
    public int search(String pattern, String text) {
        if (pattern == null || "".equals(pattern))
            throw new IllegalArgumentException();

        for (int i = 0; i + pattern.length() <= text.length(); ++i) {
            if (pattern.equals(text.substring(i, i + pattern.length())))
                return i;
        }

        return text.length();
    }
}
