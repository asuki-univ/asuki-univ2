package stringsearch;

public class KMPStringSearch implements StringSearcher {

    @Override
    public int search(String pattern, String text) {
        if (pattern == null || "".equals(pattern))
            throw new IllegalArgumentException();
        
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
        
        // We couldn't find the |pattern| in text.
        return text.length();
    }
    
    private int[] makeTable(String w) {
        int[] T = new int[w.length() + 1];
        
        int i = 2;
        int j = 0;
        
        T[0] = -1;
        T[1] = 0;
        
        while (i < w.length()) {
            if (w.charAt(i - 1) == w.charAt(j)) {
                T[i] = j + 1;
                i = i + 1;
                j = j + 1;
            } else if (j > 0) {
                j = T[j];
            } else {
                T[i] = 0;
                i = i + 1;
            }
        }
        
        return T;
    }
}
