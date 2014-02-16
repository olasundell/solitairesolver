package se.atrosys.solitaire.cardstuff.piles.pilerules;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.Suit;

public class AlternatingColorDescendingRuleTest {
	AlternatingColorDescendingRule rule = new AlternatingColorDescendingRule();

	@Test
	public void differentColorsAndAdjacentNumbersShouldWork() throws Exception {
		Assert.assertTrue(rule.eligible(new Card(Suit.CLUBS, 10), new Card(Suit.HEARTS, 9)));
	}

	@Test
	public void sameColorAndAdjacentNumbersShouldNotWork() {
		Assert.assertFalse(rule.eligible(new Card(Suit.CLUBS, 10), new Card(Suit.SPADES, 9)));
	}

	@Test
	public void differentColorAndNonAdjacentNumbersShouldNotWork() {
		Assert.assertFalse(rule.eligible(new Card(Suit.DIAMONDS, 10), new Card(Suit.SPADES, 11)));
	}
}
