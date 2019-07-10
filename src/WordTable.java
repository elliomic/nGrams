import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;
import java.util.Iterator;

public class WordTable {
	private Map<String, WordEntry> map;
	private List<WordEntry> list;
	
	public WordTable() {
		this.map = new HashMap<String, WordEntry>();
	}
	
	WordEntry recordWord(String word) {
		word = word.toLowerCase();
		WordEntry entry = findEntry(word);
		if (entry == null) {
			entry = new WordEntry(word);
			map.put(word, entry);
		}
		entry.frequency++;
		return entry;
	}
	
	WordEntry findEntry(String word) {
		return map.get(word);
	}
	
	void processEntries() {
		list = new ArrayList<WordEntry>();
		Iterator<Entry<String, WordEntry>> mapIterator = map.entrySet().iterator();
		while (mapIterator.hasNext()) {
			list.add(mapIterator.next().getValue());
		}
		ListUtility.sort(list);
		float numWords = 0;
		for (int i = 0; i < list.size(); i++) {
			numWords += list.get(i).frequency;
		}
		for (int i = 0; i < list.size(); i++) {
			WordEntry entry = list.get(i);
			entry.probability = entry.frequency / numWords;

			if (i == 0) {
				entry.cumulativeProbability = entry.probability;
			} else {
				entry.cumulativeProbability = entry.probability + list.get(i - 1).cumulativeProbability;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			WordEntry entry = list.get(i);
			entry.getTable().processEntries();
		}
	}
	
	WordEntry randomEntry() {
		double r = Math.random();
		int i = 0;
		while (list.get(i).cumulativeProbability < r) {
			i++;
		}
		return list.get(i);
	}
	
	String displayFirstWords(int numWords, int indent) {
		String val = "";
		for (int i = 0; i < numWords; i++) {
			WordEntry current = list.get(i);
			val = val.concat(current.toString() + "\n");
			try {
				val = val.concat(current.getTable().displayFirstWords(numWords/2 + 1, indent + 1));
			} catch (Exception e) {}
		}
		return val.replaceAll("\n", "\n    ");
	}
}