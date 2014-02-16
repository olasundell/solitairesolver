package se.atrosys.solitaire.cardstuff.piles.pilerules;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.Suit;

public class SameSuitAscendingRuleTest {
	SameSuitAscendingRule rule = new SameSuitAscendingRule();

	@Test
	public void differentSuitsAndAdjacentNumbersShouldNotWork() throws Exception {
		Assert.assertFalse(rule.eligible(new Card(Suit.CLUBS, 10), new Card(Suit.HEARTS, 9)));
	}

	@Test
	public void sameSuitAndAdjacentNumbersShouldWork() {
		Assert.assertTrue(rule.eligible(new Card(Suit.CLUBS, 9), new Card(Suit.CLUBS, 10)));
	}

	@Test
	public void differentSuitsAndNonAdjacentNumbersShouldNotWork() {
		Assert.assertFalse(rule.eligible(new Card(Suit.DIAMONDS, 10), new Card(Suit.SPADES, 11)));
	}
}
