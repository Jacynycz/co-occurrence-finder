package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import exceptions.ElementNotFoundException;

public class Stopwords {

	public static boolean isValidName(String text) {
		Pattern pattern = Pattern
				.compile(
						"# Match a valid Windows filename (unspecified file system).          \n"
								+ "^                                # Anchor to start of string.        \n"
								+ "(?!                              # Assert filename is not: CON, PRN, \n"
								+ "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
								+ "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
								+ "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
								+ "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
								+ "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
								+ "  $                              # and end of string                 \n"
								+ ")                                # End negative lookahead assertion. \n"
								+ "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
								+ "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
								+ "$                                # Anchor to end of string.            ",
						Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
								| Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(text);
		boolean isMatch = matcher.matches();
		return isMatch;
	}

	private FrequencyTable staticStopwords;

	private FrequencyTable temporalStopwords;

	private FrequencyTable creaVacias() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("stopwords.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FrequencyTable staticStopwords = new FrequencyTable();
		while (scanner.hasNextLine()) {
			staticStopwords.addOne(scanner.nextLine());
			// Log.log(staticStopwords);
		}
		scanner.close();
		return staticStopwords;
	}

	public Stopwords() {
		staticStopwords = creaVacias();
		temporalStopwords = new FrequencyTable();
	}

	public boolean isIn(String search) {
		if (staticStopwords.contains(search))
			return true;
		if (temporalStopwords.contains(search))
			return true;
		else
			return false;
	}

	public void addStaticStopword(String s) {
		
		staticStopwords.addUnique(s);
	}

	public void deleteStaticStopword(String toDelete) throws ElementNotFoundException {

		staticStopwords.deleteElem(toDelete);
	}

	public void addTemporalStopword(String s) {

		temporalStopwords.addUnique(s);
	}

	public void deleteTemporalStopword(String toDelete)
			throws ElementNotFoundException {

		temporalStopwords.deleteElem(toDelete);
	}
	
	
 
}
