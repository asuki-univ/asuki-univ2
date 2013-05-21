package stringsearch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RabinKarpStringSetSearch {
    public int search(Set<String> patternSet, String text) {
        Map<Integer, List<String>> patternHashes = makePatternHashMap(patternSet);

        int patternLength = patternSet.iterator().next().length();
        int textHash = hash(text.substring(0, patternLength));

        int i = 0;
        while (true) {
            if (patternHashes.containsKey(textHash)) {
                List<String> patterns = patternHashes.get(textHash);
                String substring = text.substring(i, i + patternLength);
                for (String pattern : patterns) {
                    if (pattern.equals(substring))
                        return i;
                }
            }
        
            if (text.length() <= i + patternLength)
                return text.length();

            textHash = textHash - text.charAt(i) + text.charAt(i + patternLength);
            ++i;
        }
    }

    private Map<Integer, List<String>> makePatternHashMap(Set<String> patternSet) {
        int patternLength = -1;
        
        Map<Integer, List<String>> patternHashes = new HashMap<Integer, List<String>>();
        for (String pattern : patternSet) {
            if (patternLength < 0)
                patternLength = pattern.length();
            else if (patternLength != pattern.length())
                throw new IllegalArgumentException();
        
            int patternHash = hash(pattern);
            List<String> patterns = patternHashes.get(patternHash);
            if (patterns != null) {
                patterns.add(pattern);
            } else {
                patterns = new ArrayList<String>();
                patterns.add(pattern);
            }
            
            patternHashes.put(patternHash, patterns);
        }
        
        return patternHashes;
    }
    
    private int hash(String word) {
        int result = 0;
        for (int i = 0; i < word.length(); ++i)
            result += word.charAt(i);

        return result;
    }
}
