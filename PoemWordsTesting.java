package PoemWords;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

/**
 * Test the program to make sure changes are implemented correctly
 * 
 *  @author Jean Dalmont
 */
public class PoemWordsTesting {

	/**
	 * Testing method
	 *
	 */
	@Test
	public void testMyWords() throws Throwable {

		PoemWordsWords underTest = new PoemWordsWords();

		String expectedResult = PoemWordsWords.myWords();	
		String result = underTest.myWords("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");

		assertEquals(expectedResult, result);
	}
}

