package model;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class WordParser {
	
	private Stopwords stopwords;
	
	public static String coreUrl;
	
	private static final String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";

	public WordParser() {
		
		this.stopwords=new Stopwords();
	}
	
	private void log(String message){
	
		ModelCallback.log(message);
	} 

	private void extractMonograms(String[] words, FrequencyTable table) {
		if (words.length > 0) {
			for (int i = 0; i < words.length; i++) {
				if (!stopwords.isIn(words[i].toLowerCase()))
					table.addOne(words[i].toLowerCase());
			}
		}
	}

	private void extractBigrams(String[] words, FrequencyTable table) {
		if (words.length > 0) {
			for (int i = 0; i < words.length - 1; i++) {
				if ((!stopwords.isIn(words[i].toLowerCase()))
						&& (!stopwords.isIn(words[i + 1].toLowerCase())))
					table.addBigram(words[i], words[i + 1]);
			}
		}
	}

	private void extractTrigrams(String[] words, FrequencyTable table) {
		if (words.length > 0) {
			for (int i = 0; i < words.length - 2; i++) {
				if ((!stopwords.isIn(words[i].toLowerCase()))
						&& (!stopwords.isIn(words[i + 1].toLowerCase()))
						&& (!stopwords.isIn(words[i + 2].toLowerCase())))
					table.addTrigram(words[i], words[i + 1],
							words[i + 2]);
			}
		}
	}

	private String normalize(String s) {
		String sol = s.toLowerCase();
		sol = sol.replace("á", "a");
		sol = sol.replace("é", "e");
		sol = sol.replace("í", "i");
		sol = sol.replace("ó", "o");
		sol = sol.replace("ú", "u");
		return sol;
	}

	public void extractCooccurrenceWords(String url, FrequencyTable monogramTable,
			FrequencyTable bigramTable, FrequencyTable trigramTable, int timeout) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(ua).timeout(timeout).get();
		} catch (Exception e) {
			//throw new e
			log(e.getMessage());
			return;
		}
		Elements body = doc.select("body");
		for (Element words : body) {
			String s = this.normalize(words.text());
			String[] pals = s.split(" ");
			extractMonograms(pals, monogramTable);
			extractBigrams(pals, bigramTable);
			extractTrigrams(pals, trigramTable);
		}
	}

	public boolean findReciprocalWord(String[] s, String searchWord) {
		String total = "";
		if (s.length > 0) {
			for (int i = 0; i < s.length; i++) {
				if (!stopwords.isIn(s[i].toLowerCase()))
					total += s[i].toLowerCase() + "_";
			}
		}
		if (total.contains(searchWord))
			return true;

		else
			return false;
	}

	public void lookForReciprocalWords(String url, String reciprocalWord,
			String searchWord, FrequencyTable table, int timeout) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(ua).timeout(timeout).get();
		} catch (Exception e) {
			log(e.getMessage());
			return;
		}
		Elements body = doc.select("body");
		for (Element word : body) {
			String s = this.normalize(word.text());
			String[] pals = s.split(" ");
			if (findReciprocalWord(pals, searchWord)) {
				table.addOneIgnoreSymbols(reciprocalWord);
			}
		}
	}

	public static void setCoreUrl(String url) {
		coreUrl = url;
	}

	public static Boolean checkCoreUrl(String s) {
		if (s.length() == 0)
			return false;
		String alt = coreUrl.replace("www.", "");
		if (s.length() < alt.length())
			return false;
		if (s.startsWith(coreUrl))
			return true;
		if (s.startsWith(alt))
			return true;
		else
			return false;
	}
}
