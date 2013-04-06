package stringsearch;

public class RabinKarpStringSearch extends AbstractStringSearch {
	@Override
	public int search(String word, String text) {
		if (word == null || "".equals(word))
			throw new IllegalArgumentException();

		if (text.length() < word.length())
			return text.length();

		int wordHash = hash(word);
		int textHash = hash(text.substring(0, word.length()));
		
		int i = 0;
		while (true) {
			if (wordHash == textHash && word.equals(text.substring(i, i + word.length())))
				return i;

			if (text.length() <= i + word.length())
				return text.length();

			textHash = textHash - text.charAt(i) + text.charAt(i + word.length());
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
