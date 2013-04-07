package stringsearch;

public class KMPStringSearch extends AbstractStringSearch {

    @Override
    public int search(String pattern, String text) {
        if (pattern == null || "".equals(pattern))
            throw new IllegalArgumentException();
        
        int[] table = makeTable(pattern);
        
        int m = 0;
        int i = 0;
        
        while (m + i < text.length()) {
            if (pattern.charAt(i) == text.charAt(m + i)) {
                i = i + 1;
                if (i == pattern.length())
                    return m;
            } else {
                m = m + i - table[i];
                if (i > 0)
                    i = table[i];
            }
        }
        
        // We couldn't find the |word| in text.
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
