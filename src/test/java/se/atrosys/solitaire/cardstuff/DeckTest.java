package se.atrosys.solitaire.cardstuff;

import org.testng.annotations.Test;

public class DeckTest {
	@Test(expectedExceptions = EmptyDeckException.class )
	public void shouldThrowException() throws EmptyDeckException {
		Deck deck = new Deck();
		deck.takeRemaining();
		deck.takeNext();
	}
}
