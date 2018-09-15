package core;

import org.junit.Test;
import junit.framework.TestCase;

public class GameClassTests extends TestCase {
	
	@Test
	public void testValidateInputValidString() {
		assertEquals("C", Game.validateInput("C"));
		assertEquals("C", Game.validateInput("c"));
		
		assertEquals("F", Game.validateInput("F"));
		assertEquals("F", Game.validateInput("f"));
		
		assertEquals("H", Game.validateInput("H"));
		assertEquals("H", Game.validateInput("h"));
		
		assertEquals("S", Game.validateInput("S"));
		assertEquals("S", Game.validateInput("s"));
	}
	
	@Test
	public void testValidateInputInvalidString() {
		assertEquals(null, Game.validateInput("invalid"));
		assertEquals(null, Game.validateInput(2));
		assertEquals(null, Game.validateInput(2.4));
		assertEquals(null, Game.validateInput(""));
	}
	
	@Test
	public void testSetGameState() {
		Game.GameState gameState = Game.getGameState("Q");
		assertEquals(Game.GameState.quit, gameState);
		
		gameState = Game.getGameState("F");
		assertEquals(Game.GameState.file, gameState);
		
		gameState = Game.getGameState("C");
		assertEquals(Game.GameState.console, gameState);
	}
	
	@Test
	public void testSetInvalidGameState() {
		Game.GameState gameState = Game.getGameState("");
		assertEquals(Game.GameState.invalid, gameState);
		
		gameState = Game.getGameState(123);
		assertEquals(Game.GameState.invalid, gameState);
		
		gameState = Game.getGameState("abc");
		assertEquals(Game.GameState.invalid, gameState);
	}
	
}
