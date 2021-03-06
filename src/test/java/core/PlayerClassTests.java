package core;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class PlayerClassTests extends TestCase {
	
	@Before
	public void init() {
		ConsoleIO.init();
	}
	
	@After
	public void close() {
		ConsoleIO.close();
	}
	
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
		
		handCopy.add(player.hand.hit(deck));
		
		assertEquals(handCopy.getScore(), player.hand.getScore());
	}
	
	@Test
	public void testPlayerGetBestHandState() {
		Deck deck = new Deck();
		Player player = new Player(deck);
		
		// single hand, safe
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		assertEquals(Hand.HandState.safe, player.getBestHandState());
		
		// single hand, busted
		player.hand.add(new Card("D", "10", 10));
		player.hand.add(new Card("D", "K", 10));
		assertEquals(Hand.HandState.busted, player.getBestHandState());
		
		// single hand, blackjack
		player.hand = new Hand();
		player.hand.add(new Card("D", "A", 0));
		player.hand.add(new Card("D", "K", 10));
		assertEquals(Hand.HandState.blackjack, player.getBestHandState());
		
		// split hand, both safe
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "9", 9));
		assertEquals(Hand.HandState.safe, player.getBestHandState());
		
		// split hand, split hand busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "6", 6));
		player.splitHand.add(new Card("D", "9", 9));
		player.splitHand.add(new Card("D", "8", 8));
		assertEquals(Hand.HandState.safe, player.getBestHandState());
		
		// split hand, first hand busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "9", 9));
		player.hand.add(new Card("D", "5", 5));
		player.hand.add(new Card("D", "8", 8));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "9", 9));
		assertEquals(Hand.HandState.safe, player.getBestHandState());
		
		// split hand, split hand blackjack
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "K", 10));
		assertEquals(Hand.HandState.blackjack, player.getBestHandState());
		
		// split hand, first hand blackjack
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "Q", 10));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "5", 5));
		assertEquals(Hand.HandState.blackjack, player.getBestHandState());
		
		// split hand, both hands busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "9", 9));
		player.hand.add(new Card("D", "5", 5));
		player.hand.add(new Card("D", "8", 8));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "6", 6));
		player.splitHand.add(new Card("D", "9", 9));
		player.splitHand.add(new Card("D", "8", 8));
		assertEquals(Hand.HandState.busted, player.getBestHandState());
	}
	
	@Test
	public void testPlayerGetBestScore() {
		Deck deck = new Deck();
		Player player = new Player(deck);
		
		// single hand, safe
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		assertEquals(16, player.getBestHandScore());
		
		// single hand, busted
		player.hand.add(new Card("D", "10", 10));
		player.hand.add(new Card("D", "K", 10));
		assertEquals(0, player.getBestHandScore());
		
		// single hand, blackjack
		player.hand = new Hand();
		player.hand.add(new Card("D", "A", 0));
		player.hand.add(new Card("D", "K", 10));
		assertEquals(21, player.getBestHandScore());
		
		// split hand, both safe
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "9", 9));
		assertEquals(20, player.getBestHandScore());
		
		// split hand, split hand busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "6", 6));
		player.splitHand.add(new Card("D", "9", 9));
		player.splitHand.add(new Card("D", "8", 8));
		assertEquals(16, player.getBestHandScore());
		
		// split hand, first hand busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "9", 9));
		player.hand.add(new Card("D", "5", 5));
		player.hand.add(new Card("D", "8", 8));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "9", 9));
		assertEquals(20, player.getBestHandScore());
		
		// split hand, split hand blackjack
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "5", 5));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "K", 10));
		assertEquals(21, player.getBestHandScore());
		
		// split hand, first hand blackjack
		player.hand = new Hand();
		player.hand.add(new Card("H", "A", 0));
		player.hand.add(new Card("D", "Q", 10));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "A", 0));
		player.splitHand.add(new Card("D", "5", 5));
		assertEquals(21, player.getBestHandScore());
		
		// split hand, both hands busted
		player.hand = new Hand();
		player.hand.add(new Card("H", "9", 9));
		player.hand.add(new Card("D", "5", 5));
		player.hand.add(new Card("D", "8", 8));
		player.splitHand = new Hand();
		player.splitHand.add(new Card("D", "6", 6));
		player.splitHand.add(new Card("D", "9", 9));
		player.splitHand.add(new Card("D", "8", 8));
		assertEquals(0, player.getBestHandScore());
	}
	
	@Test
	public void testDealerPlayerShowCards() {
		Deck deck = new Deck();
		DealerPlayer dealer = new DealerPlayer(deck);
		
		dealer.hand = new Hand();
		dealer.hand.add(new Card("C", "8", 8));
		assertEquals("Dealer's Hand: C8", dealer.getHand(false));
		
		dealer.hand.add(new Card("H", "A", 0));
		dealer.hand.add(new Card("D", "5", 5));
		assertEquals("Dealer's Hand: C8, HA, D5", dealer.getHand(true));
	}
	
	@Test
	public void testHumanPlayerShowCards() {
		Deck deck = new Deck();
		HumanPlayer human = new HumanPlayer(deck);
		
		human.hand = new Hand();
		human.hand.add(new Card("C", "8", 8));
		assertEquals("Your Hand: C8", human.getHand());
		
		human.hand.add(new Card("H", "A", 0));
		human.hand.add(new Card("D", "5", 5));
		assertEquals("Your Hand: C8, HA, D5", human.getHand());
	}
	
	@Test
	public void testDealerPlayerHit() {
		Deck deck = new Deck();
		DealerPlayer dealer = new DealerPlayer(deck);
		Game.human = new HumanPlayer(deck);
		
		// if dealer.getHandScore() <= 16, hit
		dealer.hand = new Hand();
		dealer.currentHand = dealer.hand;
		dealer.hand.add(new Card("C", "8", 8));
		assertTrue(dealer.checkHit());
		
		// if dealer.getHandScore() <= 16, hit
		dealer.hand.add(new Card("H", "6", 6));
		dealer.hand.add(new Card("H", "2", 2));
		assertTrue(dealer.checkHit());
		
		// if dealer.getHandScore() > 16 (and not soft 17), stay
		dealer.hand.add(new Card("C", "4", 8));
		assertFalse(dealer.checkHit());
		
		// if dealer.getHandScore() = 17 (soft 17), hit
		dealer.hand = new Hand();
		dealer.currentHand = dealer.hand;
		dealer.hand.add(new Card("C", "A", 0));
		dealer.hand.add(new Card("C", "3", 3));
		dealer.hand.add(new Card("C", "2", 2));
		assertTrue(dealer.checkHit());
		
		// if dealer.getHandScore() > 17 (with A), stay
		dealer.hand.add(new Card("H", "3", 3));
		assertFalse(dealer.checkHit());
	}
	
	@Test
	public void testDealerPlayerTurn() {
		Deck deck = new Deck();
		deck.shuffle();
		DealerPlayer dealer = new DealerPlayer(deck);
		Game.human = new HumanPlayer(deck);
		
		ConsoleIO.outputln(System.lineSeparator());
		ConsoleIO.outputln("Full hand for dealer player turn test: " + dealer.getHand(true));
		dealer.takeTurn(deck);
		assertThat(dealer.getBestHandState(), anyOf(is(Hand.HandState.blackjack), is(Hand.HandState.safe), is(Hand.HandState.busted)));
		
		
		// dealer's hand adds to 17 or less, so split
		deck = new Deck();
		deck.cards = new ArrayList<Card>();
		deck.cards.add(new Card("C", "8", 8)); // dealer's first card
		deck.cards.add(new Card("D", "8", 8)); // dealer's second card
		dealer = new DealerPlayer(deck);
		deck.cards.add(new Card("H", "10", 10)); // human's first card
		deck.cards.add(new Card("D", "7", 7));   // human's second card
		Game.human = new HumanPlayer(deck);
		deck.cards.add(new Card("C", "5", 5));   // automatically added to dealer's first hand. Dealer should hit first hand
		deck.cards.add(new Card("H", "5", 5));   // automatically added to dealer's split hand. Dealer should hit split hand
		deck.cards.add(new Card("C", "10", 10)); // added to dealer's first hand on hit. Dealer busts first hand (with a 23)
		deck.cards.add(new Card("H", "7", 7));   // added to dealer's split hand on hit. Dealer should stay split hand (with a 20)
		dealer.takeTurn(deck);
		
		assertEquals(true, dealer.split);
		assertEquals(23, dealer.hand.getScore());
		assertEquals(3, dealer.hand.cards.size());
		assertEquals(20, dealer.splitHand.getScore());
		assertEquals(3, dealer.splitHand.cards.size());
		
		// force the player to bust, and the dealer shouldn't need to take their turn
		deck = new Deck();
		dealer = new DealerPlayer(deck);
		Game.human = new HumanPlayer(deck);
		Game.human.hand.add(new Card("S", "8", 8));
		Game.human.hand.add(new Card("C", "K", 10));
		Game.human.hand.add(new Card("S", "Q", 10));
		
		dealer.takeTurn(deck);
		assertEquals(Hand.HandState.busted, Game.human.getBestHandState());
		assertEquals(2, dealer.hand.cards.size());
	}
	
	@Test
	public void testHumanPlayerTurn() {
		Deck deck = new Deck();
		deck.shuffle();
		HumanPlayer human = new HumanPlayer(deck);
		
		ConsoleIO.fileCommands = null;
		ConsoleIO.inputError = false;
		ConsoleIO.outputln(System.lineSeparator());
		ConsoleIO.outputln(human.getHand());
		human.takeTurn(deck);
		
		assertThat(human.getBestHandState(), anyOf(is(Hand.HandState.blackjack), is(Hand.HandState.safe), is(Hand.HandState.busted)));
	}
	
	@Test
	public void testHumanPlayerSplitTurn() {
		Deck deck = new Deck();
		HumanPlayer human;
		
		deck.cards = new ArrayList<Card>();
		deck.cards.add(new Card("C", "8", 8)); // human's first card
		deck.cards.add(new Card("D", "8", 8)); // human's second card
		human = new HumanPlayer(deck);
		deck.cards.add(new Card("C", "5", 5));   // automatically added to human's first hand.
		deck.cards.add(new Card("H", "5", 5));   // automatically added to human's split hand.
		deck.cards.add(new Card("C", "10", 10));
		deck.cards.add(new Card("H", "7", 7));
		
		ConsoleIO.fileCommands = null;
		ConsoleIO.inputError = false;
		ConsoleIO.outputln(System.lineSeparator());
		ConsoleIO.outputln(human.getHand());
		human.takeTurn(deck);
		
		assertEquals(true, human.split);
	}
	
	@Test
	public void testPlayerSplit() {
		Deck deck = new Deck();
		Player player = new Player(deck);
		
		player.hand = new Hand();
		player.hand.add(new Card("C", "8", 8));
		player.hand.add(new Card("D", "8", 8));
		
		player.split(deck);
		assertEquals(true, player.split);
		assertNotSame(player.hand, player.splitHand);
		assertEquals(2, player.hand.cards.size());
		assertEquals(2, player.splitHand.cards.size());
		assertEquals("C8", player.hand.cards.get(0).toString());
		assertEquals("D8", player.splitHand.cards.get(0).toString());
	}
}
