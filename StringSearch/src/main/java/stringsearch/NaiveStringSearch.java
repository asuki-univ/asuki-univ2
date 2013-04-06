package stringsearch;

public class NaiveStringSearch extends AbstractStringSearch {
	@Override
	public int search(String word, String text) {
        if (word == null || "".equals(word))
            throw new IllegalArgumentException();

		for (int i = 0; i + word.length() <= text.length(); ++i) {
			if (word.equals(text.substring(i, i + word.length())))
				return i;
		}
		
		return text.length();
	}
}
