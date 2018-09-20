package core;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;



public class ConsoleIOClassTests extends TestCase {
	@Before
	public void init() {
		ConsoleIO.init();
	}
	
	@After
	public void close() {
		ConsoleIO.close();
	}
	
	@Test
	public void testValidateInputValidString() {
		assertEquals("C", ConsoleIO.validateInput("C"));
		assertEquals("C", ConsoleIO.validateInput("c"));
		
		assertEquals("F", ConsoleIO.validateInput("F"));
		assertEquals("F", ConsoleIO.validateInput("f"));
		
		assertEquals("H", ConsoleIO.validateInput("H"));
		assertEquals("H", ConsoleIO.validateInput("h"));
		
		assertEquals("S", ConsoleIO.validateInput("S"));
		assertEquals("S", ConsoleIO.validateInput("s"));
		
		assertEquals("", ConsoleIO.validateInput("invalid"));
		assertEquals("", ConsoleIO.validateInput(2));
		assertEquals("", ConsoleIO.validateInput(2.4));
		assertEquals("", ConsoleIO.validateInput(""));
	}
	
	@Test
	public void testReadInputFile() {
		ConsoleIO.readInputFile("src/main/resources/input files/file1.txt");
		
		List<String> expectedPlayerCommands = new ArrayList<String>();
		List<Card> expectedCardList = new ArrayList<Card>();
		expectedCardList.add(new Card("S", "K", 10));
		expectedCardList.add(new Card("H", "A", 0));
		expectedCardList.add(new Card("H", "Q", 10));
		expectedCardList.add(new Card("C", "A", 0));
		
		assertFalse(ConsoleIO.inputError);
		assertEquals(expectedPlayerCommands, ConsoleIO.fileCommands);
		for (int i = 0; i < Game.deck.cards.size(); i++) {
			assertEquals(expectedCardList.get(i).toString(), Game.deck.cards.get(i).toString());
		}
	}

	@Test
	public void testIsValidCard() {
		assertEquals(true, ConsoleIO.isValidCard("DA"));
		assertEquals(true, ConsoleIO.isValidCard("H8"));
		assertEquals(true, ConsoleIO.isValidCard("CQ"));
		assertEquals(true, ConsoleIO.isValidCard("SK"));
		
		assertEquals(false, ConsoleIO.isValidCard("AA"));
		assertEquals(false, ConsoleIO.isValidCard("C11"));
		assertEquals(false, ConsoleIO.isValidCard("1"));
		assertEquals(false, ConsoleIO.isValidCard(""));
		assertEquals(false, ConsoleIO.isValidCard("S"));
		assertEquals(false, ConsoleIO.isValidCard("H"));
		assertEquals(false, ConsoleIO.isValidCard("D"));
	}
	
	@Test
	public void testIsValidPlayerCommand() {	
		assertEquals(true, ConsoleIO.isValidPlayerCommand("S"));
		assertEquals(true, ConsoleIO.isValidPlayerCommand("H"));
		assertEquals(true, ConsoleIO.isValidPlayerCommand("D"));
		assertEquals(true, ConsoleIO.isValidPlayerCommand("s"));
		assertEquals(true, ConsoleIO.isValidPlayerCommand("h"));
		assertEquals(true, ConsoleIO.isValidPlayerCommand("d"));
		
		assertEquals(false, ConsoleIO.isValidPlayerCommand("DA"));
		assertEquals(false, ConsoleIO.isValidPlayerCommand("H8"));
		assertEquals(false, ConsoleIO.isValidPlayerCommand("CQ"));
		assertEquals(false, ConsoleIO.isValidPlayerCommand("SK"));
		assertEquals(false, ConsoleIO.isValidPlayerCommand("1"));
		assertEquals(false, ConsoleIO.isValidPlayerCommand(""));
	}
}
