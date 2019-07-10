import java.util.ArrayList;

public class nGrams {
	private int n = 3;
	public WordTable myTable = new WordTable();
	private ArrayList<String> allwords = new ArrayList<String>();
	
	public nGrams() {}
	
	public nGrams(int n) {
		this.n = n;
	}
	
	public void addFile(String filename) {
		SimpleFile file = new SimpleFile(filename);
		String[] words = null;
		for (String text : file) {
			words = text.replaceAll("\\s[^\\w\\s]+\\s", "").replaceAll("\\s+", " ").split("\\s");
			for (int i = 0; i < words.length; i++) {
				words[i] = words[i].trim();
				if (words[i].length() != 0) {
					allwords.add(words[i]);
				}
			}
		}

		for (int i = 0; i < allwords.size() - n + 1; i++) {
			WordEntry newWord = myTable.recordWord(allwords.get(i));
			int r = n;
			while (r > 1) {
				r--;
				newWord = newWord.getTable().recordWord(allwords.get(i+n-r));
			}
		}
		for (int i = 1; i < n; i++) {
			myTable.recordWord(allwords.get(allwords.size() - i));
		}

		allwords.clear();
	}
	
	public String write(int num) {
		String val = "";
		WordEntry[] entries = new WordEntry[n];
		entries[0] = myTable.randomEntry();
		for (int r = 0; r < n - 1; r++) {
			entries[r+1] = entries[r].getTable().randomEntry();
		}
		for (int i = 1; i <= num; i++) {
			entries[n-2] = entries[n-3].getTable().randomEntry();
			val = val.concat(entries[0].word + " ");
			entries[0] = myTable.findEntry(entries[1].word);
			for (int r = 0; r < n - 2; r++) {
				entries[r+1] = entries[r].getTable().findEntry(entries[r+2].word);
			}
			if (i % 15 == 0) {
				val = val.concat("\n");
			}
		}
		val = val.concat("\n");
		return val;
	}
}