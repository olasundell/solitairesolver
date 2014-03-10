package se.atrosys.solitaire.card;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.InputMismatchException;

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

	@Test
	public void shorthandConstructor() {
		Card c = new Card("6S");

		Assert.assertEquals(c.getRank(), 6);
		Assert.assertEquals(c.getSuit(), Suit.SPADES);

		c = new Card("AD");

		Assert.assertEquals(c.getRank(), 1);
		Assert.assertEquals(c.getSuit(), Suit.DIAMONDS);
	}

	@Test(expectedExceptions = InputMismatchException.class)
	public void shorthandConstructorShouldThrowException() {
		new Card("XS");
	}

	@Test(expectedExceptions = InputMismatchException.class)
	public void shorthandConstructorShouldThrowException2() {
		new Card("AR");
	}
}
