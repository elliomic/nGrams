public class WordEntry implements Comparable<WordEntry> {

	String word;
	int frequency;
	double probability;
	double cumulativeProbability;
	private WordTable table;

	public WordEntry(String word) {
		this.word = word;
		this.frequency = 0;
	}

	public int compareTo(WordEntry entry) {
		return this.frequency - entry.frequency;
	}

	@Override
	public String toString() {
		return "\nword: " + word + "\nfrequency: " + frequency + "\nprobability: " + probability + "\ncumulativeProbability: " + cumulativeProbability;
	}
	
	public WordTable getTable() {
		if (table == null) {
			table = new WordTable();
		}
		return table;
	}
}