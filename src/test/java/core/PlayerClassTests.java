package core;

import org.junit.Test;
import junit.framework.TestCase;

public class PlayerClassTests extends TestCase {
	
	/**************
	 * Purpose:  Test a player's 'hit' option. A deck and a player are created,
	 * 		and the player's hand is populated with the first two cards of the 
	 * 		deck (Deck.draw(), Player.Player()). The player's hand is saved with
	 * 		a shallow copy into 'handCopy'. The player hits, and the new card is
	 * 		added to handCopy.
	 **************/
	@Test
	public void testPlayerHit() {
		Deck deck = new Deck();
		Hand handCopy = new Hand();
		
		Player player = new Player(deck);
		for (Card card : player.hand.cards) {
			handCopy.add(card);
		}
		
		handCopy.add(player.hit(deck));
		
		assertEquals(handCopy.getScore(), player.getScore());
	}
	
	@Test
	public void testDealerPlayerShowCards() {
		Deck deck = new Deck();
		DealerPlayer dealer = new DealerPlayer(deck);
		
		dealer.hand = new Hand();
		dealer.hand.add(new Card("Clubs", "8", 8));
		assertEquals("Dealer's Hand: 8 of Clubs", dealer.showHand());
		
		dealer.hand.add(new Card("Hearts", "Ace", 0));
		dealer.hand.add(new Card("Diamonds", "5", 5));
		assertEquals("Dealer's Hand: 8 of Clubs, [FACE DOWN], [FACE DOWN]", dealer.showHand());
	}
	
	@Test
	public void testHumanPlayerShowCards() {
		Deck deck = new Deck();
		HumanPlayer human = new HumanPlayer(deck);
		
		human.hand = new Hand();
		human.hand.add(new Card("Clubs", "8", 8));
		assertEquals("Your Hand: 8 of Clubs", human.showHand());
		
		human.hand.add(new Card("Hearts", "Ace", 0));
		human.hand.add(new Card("Diamonds", "5", 5));
		assertEquals("Your Hand: 8 of Clubs, Ace of Hearts, 5 of Diamonds", human.showHand());
	}
	
	
	@Test
	public void testPlayerBlackjack() {
		Deck deck = new Deck();
		Player player = new Player(deck);
		player.hand = new Hand();
		
		player.hand.add(new Card("Spades", "King", 10));
		player.hand.add(new Card("Spades", "8", 8));
		assertEquals("safe", player.checkHandState());
		
		player.hand.add(new Card("Hearts", "3", 3));
		assertEquals("blackjack", player.checkHandState());
		
		player.hand.add(new Card("Hearts", "5", 5));
		assertEquals("busted", player.checkHandState());
	}
}
