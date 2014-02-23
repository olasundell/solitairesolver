package se.atrosys.solitaire.card.pile.rule;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;

public class PileComparatorTest {

	private PileComparator comparator;
	private Pile one;
	private Pile two;

	@BeforeMethod
	public void setup() {
		comparator = new PileComparator();
		one = new Pile(PileType.FOUNDATION);
		two = new Pile(PileType.FOUNDATION);
	}

	@Test
	public void shouldCompareSimpleCase() {
		one.dealCard(new Card(Suit.CLUBS, 1));
		two.dealCard(new Card(Suit.CLUBS, 1));

		Assert.assertEquals(0, comparator.compare(one, two));
	}

	@Test
	public void shouldCompareDifferent() {
		one.dealCard(new Card(Suit.CLUBS, 1));
		two.dealCard(new Card(Suit.HEARTS, 1));

		Assert.assertNotEquals(0, comparator.compare(one, two));
	}

	@Test
	public void shouldCompareDifferentSameSuit() {
		one.dealCard(new Card(Suit.CLUBS, 1));
		two.dealCard(new Card(Suit.CLUBS, 2));

		Assert.assertNotEquals(0, comparator.compare(one, two));
	}
}
