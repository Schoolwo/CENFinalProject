package PoemWords;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * This class will access the web page for the poem and count
 * each word and how often each word appear.
 * 
 * @version 1.0
 * @author Jean Dalmont
 *
 */
public class PoemWordsWords {

	/**
	 * Main method
	 * @param not used
	 *
	 */
	public static void main(String[] args) {
		System.out.println(myWords());
	}

	/**
	 * gets words from web
	 * Counts the number of each word.
	 * 
	 * @return the words and their frequency 
	 */
	public static String myWords() {
		String theWords = "";

		try {
			String s = "";
			Document document = Jsoup.connect(
					"https://www.gutenberg.org/files/1065/1065-h/1065-h.htm" )
					.timeout(0).get();

			Elements allElements = document.select("p");
			for (Element element : allElements) {
				s += element.ownText();
			}

			allElements = document.select("span[style*='margin-left: 20%']");
			for (Element element : allElements) {
				s += element.ownText();
			}

			HashMap<String, Integer> hm = new <String, Integer>HashMap();

			s = s.toLowerCase();
			String s2 = s.replaceAll("[^:a-z:]", " ");
			String[] words = s2.split("\s");
			for (int i = 0; i < words.length; i++) {
				if (words[i].length() != 0 && words[i].charAt(0) != ' ') {
					if (hm.containsKey(words[i])) {
						hm.replace(words[i], hm.get(words[i]) + 1);
					} else {
						hm.put(words[i], 1);
					}
				}
			}
			hm = sortByFrequency(hm);
			theWords = hm.entrySet().toString();

			////////////////////////////////////////////////////////////////////////////////


			Connection c = null;
			Statement stmt = null;

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:wordOccurrences.db");
				System.out.println("Database Opened...\n");

				for(Entry e:hm.entrySet()) {
					stmt = c.createStatement();
					//String sql = "insert into Word (myWords, count) values ('"+e.getKey()+"' , "+e.getValue()+");";

					//stmt.executeUpdate(sql);
					stmt.close();

					System.out.println(e);
				}

				c.close();
			}
			catch ( Exception e ) {

				System.err.println( e.getClass().getName() + ": " + e.getMessage() );

				System.exit(0);
			}



			////////////////////////////////////////////////////////////////////////////////



		} 
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}
		return theWords;
	}


	/**
	 * 2nd method
	 * @param input the URL for words to be counted
	 *
	 */
	public String myWords(String URL) {
		String theWords = "";

		try {
			String s = "";
			Document document = Jsoup.connect(
					URL )
					.timeout(0).get();

			Elements allElements = document.select("p");
			for (Element element : allElements) {
				s += element.ownText();
			}

			allElements = document.select("span[style*='margin-left: 20%']");
			for (Element element : allElements) {
				s += element.ownText();
			}

			HashMap<String, Integer> hm = new <String, Integer>HashMap();

			s = s.toLowerCase();
			String s2 = s.replaceAll("[^:a-z:]", " ");
			String[] words = s2.split("\s");
			for (int i = 0; i < words.length; i++) {
				if (words[i].length() != 0 && words[i].charAt(0) != ' ') {
					if (hm.containsKey(words[i])) {
						hm.replace(words[i], hm.get(words[i]) + 1);
					} else {
						hm.put(words[i], 1);
					}
				}
			}
			hm = sortByFrequency(hm);
			theWords = hm.entrySet().toString();

			////////////////////////////////////////////////////////////////////////////////


			Connection c = null;
			Statement stmt = null;

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:wordOccurrences.db");
				System.out.println("\n\n\nDatabase Opened...\n");

				for(Entry e:hm.entrySet()) {
					stmt = c.createStatement();
					//							String sql = "insert into Word (myWords, count) values ('"+e.getKey()+"' , "+e.getValue()+");";

					//stmt.executeUpdate(sql);
					stmt.close();

					System.out.println(e);
				}

				c.close();
			}
			catch ( Exception e ) {

				System.err.println(e.getClass().getName() + ": " + e.getMessage() );

				System.exit(0);
			}



			////////////////////////////////////////////////////////////////////////////////



		} 
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}
		return theWords;
	}

	/**
	 * sort the words and their frequency
	 * @param map the HashMap to be sorted
	 * @return sorted HashMap
	 */
	private static HashMap sortByFrequency(HashMap map) {
		List list = new LinkedList(map.entrySet());

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}

		return sortedHashMap;
	}
}