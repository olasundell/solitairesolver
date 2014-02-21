package se.atrosys.solitaire.card;

import org.testng.annotations.Test;

public class DeckTest {
	@Test(expectedExceptions = EmptyDeckException.class )
	public void shouldThrowException() throws EmptyDeckException {
		Deck deck = new Deck();
		deck.takeRemaining();
		deck.takeNext();
	}
}
