package core;


public class Player {
	public Hand hand;
	
	public Player(Deck deck) {
		hand = new Hand();
		
		hand.add(deck.draw());
		hand.add(deck.draw());
	}
	
	public Card hit(Deck deck) {
		Card newCard = deck.draw();
		hand.add(newCard);
		
		return newCard;
	}
	
	public int getScore() {
		return hand.getScore();
	}
	
	public String showHand() {
		// implemented in 'HumanPlayer' and 'DealerPlayer'.
		return null;
	}
	
	public String getHandState() {		
		return hand.checkHandState();
	}
}
