package se.atrosys.solitaire.cardstuff.piles;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.Suit;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;

public class PileTest {
	@Test
	public void cardsShouldBeAddedCorrectly() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new AlternatingColorDescendingRule());
		pile.addCard(new Card(Suit.CLUBS, 10));
		pile.addCard(new Card(Suit.DIAMONDS, 9));
		pile.addCard(new Card(Suit.CLUBS, 8));

		Assert.assertEquals(3, pile.getCards().size());
	}
}
