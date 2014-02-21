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
		Pile pile = new Pile(PileType.TABLEAU);
		pile.addCard(new Card(Suit.CLUBS, 10));
		pile.addCard(new Card(Suit.DIAMONDS, 9));
		pile.addCard(new Card(Suit.CLUBS, 8));

		Assert.assertEquals(3, pile.getCards().size());
	}
	@Test(expectedExceptions = IneligibleCardException.class)
	public void shouldThrowException() throws IneligibleCardException {
		Pile pile = new Pile(PileType.STOCK);

		pile.addCard(new Card(Suit.CLUBS, 1));
	}

	@Test
	public void aceFirstShouldWork() throws IneligibleCardException {
		Pile pile = new Pile(PileType.FOUNDATION);
		pile.addCard(new Card(Suit.CLUBS, 1));
	}

	@Test
	public void twoSecondShouldWork() throws IneligibleCardException {
		Pile pile = new Pile(PileType.FOUNDATION);
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.CLUBS, 2));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void nonAceFirstShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile(PileType.FOUNDATION);
		pile.addCard(new Card(Suit.CLUBS, 2));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void nonTwoSecondShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile(PileType.FOUNDATION);
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.CLUBS, 3));
	}

	@Test(expectedExceptions = IneligibleCardException.class)
	public void differentSuitSecondShouldNotWork() throws IneligibleCardException {
		Pile pile = new Pile(PileType.FOUNDATION);
		pile.addCard(new Card(Suit.CLUBS, 1));
		pile.addCard(new Card(Suit.SPADES, 2));
	}

    @Test
    public void pilesShouldEqualWhenOrdered() {
        Pile p1 = new Pile(PileType.FOUNDATION);
        Pile p2 = new Pile(PileType.FOUNDATION);

        p1.dealCard(new Card(Suit.CLUBS, 1));
        p1.dealCard(new Card(Suit.CLUBS, 2));
        p2.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 2));

        Assert.assertEquals(p1, p2);
    }

    @Test
    public void pilesShouldNotEqualWhenOrdered() {
        Pile p1 = new Pile(PileType.FOUNDATION);
        Pile p2 = new Pile(PileType.FOUNDATION);

        p1.dealCard(new Card(Suit.CLUBS, 2));
        p1.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 2));

        Assert.assertNotEquals(p1, p2);
    }

    @Test
    public void pilesShouldEqualWhenUnorderedYetWithOrder() {
        Pile p1 = new Pile(PileType.STOCK);
        Pile p2 = new Pile(PileType.STOCK);

        p1.dealCard(new Card(Suit.CLUBS, 1));
        p1.dealCard(new Card(Suit.CLUBS, 2));
        p2.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 2));

        Assert.assertEquals(p1, p2);
    }

    @Test
    public void pilesShouldEqualWhenTrulyUnordered() {
        Pile p1 = new Pile(PileType.STOCK);
        Pile p2 = new Pile(PileType.STOCK);

        p1.dealCard(new Card(Suit.CLUBS, 2));
        p1.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 1));
        p2.dealCard(new Card(Suit.CLUBS, 2));

        Assert.assertEquals(p1, p2);
    }
}
