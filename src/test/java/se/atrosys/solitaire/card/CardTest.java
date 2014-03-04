package se.atrosys.solitaire.card;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CardTest {
	@Test
	public void compareToShouldWorkWithSameSuit() {
		Card one = new Card(Suit.CLUBS, 1);
		Card two = new Card(Suit.CLUBS, 3);

		int actual = one.compareTo(two);
		Assert.assertNotEquals(actual, 0);
		Assert.assertTrue(actual < 0);
	}

	@Test
	public void compareToShouldWorkWithDifferentSuitsSameRank() {
		Card one = new Card(Suit.CLUBS, 1);
		Card two = new Card(Suit.HEARTS, 1);

		int actual = one.compareTo(two);
		Assert.assertNotEquals(actual, 0);
		Assert.assertTrue(actual < 0);
	}

	@Test
	public void comparingEqualCardsShouldBeZero() {
		Card one = new Card(Suit.CLUBS, 10);
		Card two = new Card(Suit.CLUBS, 10);

		int actual = one.compareTo(two);
		Assert.assertEquals(actual, 0);
	}

}
