package model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/*
 * Class : FrequencyTable
 * Tables used by the model to calculate frequency values
 * 
 * */

public class FrequencyTable {

	private Hashtable<String, Integer> table;

	private int total;

	public FrequencyTable() {
		this.table = new Hashtable<String, Integer>();
		total = 0;
	}

	/*
	 * addOne: adds one element to the table, updating the frequency of
	 * apparition
	 * 
	 * Input: String containing the element
	 */
	public void addOne(String elem) {

		if (elem.equalsIgnoreCase(""))
			return;

		if (!this.check(elem))
			return;

		if (this.table.containsKey(elem)) {

			int i = this.table.get(elem);

			this.table.put(elem, i + 1);

		}

		else

			this.table.put(elem, 1);

		this.total += 1;
	}

	/*
	 * addOneIgnoreSymbols: adds one element to the table, ignoring symbols,
	 * updating the frequency of apparition
	 * 
	 * Input: String containing the element
	 */
	public void addOneIgnoreSymbols(String elem) {

		if (elem.equalsIgnoreCase(""))
			return;

		if (this.table.containsKey(elem)) {

			int i = this.table.get(elem);

			this.table.put(elem, i + 1);

		}

		else

			this.table.put(elem, 1);

		this.total += 1;
	}

	public boolean addUnique(String s) {

		if (this.table.containsKey(s))

			return false;

		else {

			this.addOne(s);

			return true;

		}
	}

	public void addBigram(String first, String second) {

		if (!this.check(first))
			return;

		if (!this.check(second))
			return;

		String elem = first + "_" + second;

		if (this.table.containsKey(elem)) {

			int i = this.table.get(elem);

			this.table.put(elem, i + 1);

		} else

			this.table.put(elem, 1);

		this.total += 1;
	}

	public void addTrigram(String first, String second, String thid) {

		if (!this.check(first))
			return;

		if (!this.check(second))
			return;

		if (!this.check(thid))
			return;

		String elem = first + "_" + second + "_" + thid;

		if (this.table.containsKey(elem)) {

			int i = this.table.get(elem);

			this.table.put(elem, i + 1);

		} else

			this.table.put(elem, 1);

		this.total += 1;
	}

	public Integer getElemFrequency(String elem) {

		if (this.table.contains(elem)) {

			return this.table.get(elem);

		} else

			return -1;
	}

	public String toString() {

		return this.table.toString();

	}

	private void quicksort(int elemFrequencies[], String s[], int low,
			int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = elemFrequencies[low + (high - low) / 2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (elemFrequencies[i] < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (elemFrequencies[j] > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				int temp = elemFrequencies[i];
				String temp2 = s[i];

				elemFrequencies[i] = elemFrequencies[j];
				s[i] = s[j];

				elemFrequencies[j] = temp;
				s[j] = temp2;
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksort(elemFrequencies, s, low, j);
		if (i < high)
			quicksort(elemFrequencies, s, i, high);
	}

	public int getTableSize() {

		return this.table.size();

	}

	public boolean deleteElem(String s) {

		if (this.table.containsKey(s)) {

			this.table.remove(s);

			return true;

		} else

			return false;
	}

	public int getFreqAbsolute() {

		return this.total;

	}

	private boolean check(String s) {

		if (s.equalsIgnoreCase(""))
			return false;

		String letras = "ÁÉÍÓÚáéíóúabcdefghijklmnñopqrstuvwxyz";

		s = s.toLowerCase();

		for (int i = 0; i < s.length(); i++) {

			if (letras.indexOf(s.charAt(i), 0) == -1)
				return false;
		}
		return true;
	}

	public void nMayores(String[] arrayWords, Float[] relFrequencies,
			int[] absFrequencies) {
		
		Vector<String> elementsVector = new Vector<String>(this.table.keySet());
		
		Integer size = elementsVector.size();
		
		if (size == 0)
			return;

		String[] s = new String[size];
		
		int[] elemFrequencies = new int[size];
		
		int i = 0;
		
		for (String elem : elementsVector) {
			
			s[i] = elem;
			
			elemFrequencies[i] = this.table.get(elem);
			
			i++;
		}
		this.quicksort(elemFrequencies, s, 0, i - 1);
		
		int arrayWordsLength = arrayWords.length;
		
		if (elemFrequencies.length < arrayWords.length)
			
			arrayWordsLength = elemFrequencies.length;
		
		for (int j = 1; j <= arrayWordsLength; j++) {
			
			arrayWords[j - 1] = s[i - j];
			
			absFrequencies[j - 1] = elemFrequencies[i - j];
			
			Float fab = (float) elemFrequencies[i - j];
			
			Float total = (float) this.total;
			
			relFrequencies[j - 1] = fab / total * 100;
		}
	}

	@SuppressWarnings("unchecked")
	public void joinTables(FrequencyTable otra) {
		
		this.table.putAll((Map<String, Integer>) otra);
		
	}
}
