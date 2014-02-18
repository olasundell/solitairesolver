package se.atrosys.solitaire.cardstuff.piles;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.Suit;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;

public class PileTest {
	@Test
	public void cardsShouldBeAddedCorrectly() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new AlternatingColorDescendingRule());
		pile.addCard(new Card(Suit.CLUBS, 10));
		pile.addCard(new Card(Suit.DIAMONDS, 9));
		pile.addCard(new Card(Suit.CLUBS, 8));

		Assert.assertEquals(3, pile.getCards().size());
	}
	@Test(expectedExceptions = IneligibleCardException.class)
	public void shouldThrowException() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new Rule() {
			@Override
			public boolean eligible(Card existing, Card intended) {
				return false;
			}
		});

		pile.addCard(new Card(Suit.CLUBS, 1));
	}

	@Test
	public void aceFirstShouldWork() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new SameSuitAscendingAceFirstRule());
		pile.addCard(new Card(Suit.CLUBS, 1));
	}

	@Test
	public void twoSecondShouldWork() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new SameSuitAscendingAceFirstRule());
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.CLUBS, 2));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void nonAceFirstShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new SameSuitAscendingAceFirstRule());
		pile.addCard(new Card(Suit.CLUBS, 2));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void nonTwoSecondShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new SameSuitAscendingAceFirstRule());
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.CLUBS, 3));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void differentSuitSecondShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile().withRule(new SameSuitAscendingAceFirstRule());
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.SPADES, 2));
	}
}
