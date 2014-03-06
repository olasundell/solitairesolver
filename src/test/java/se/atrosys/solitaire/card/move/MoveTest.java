package se.atrosys.solitaire.card.move;

import org.testng.Assert;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;

import java.util.Arrays;

public class MoveTest {
	@Test
	public void shouldBeEqual() {
		Move one = new Move(new Pile(PileType.TABLEAU), new Pile(PileType.TABLEAU), new Card(Suit.CLUBS, 5));
		one.withFollowers(Arrays.asList(new Card(Suit.DIAMONDS, 4), new Card(Suit.SPADES, 3), new Card(Suit.DIAMONDS, 2)));
		Move two = new Move(new Pile(PileType.TABLEAU), new Pile(PileType.TABLEAU), new Card(Suit.CLUBS, 5));
		two.withFollowers(Arrays.asList(new Card(Suit.DIAMONDS, 4), new Card(Suit.SPADES, 3), new Card(Suit.DIAMONDS, 2)));

		Assert.assertEquals(one, two);
	}

	@Test
	public void shouldNotBeEqual() {
		Move one = new Move(new Pile(PileType.TABLEAU), new Pile(PileType.TABLEAU), new Card(Suit.CLUBS, 5));
		one.withFollowers(Arrays.asList(new Card(Suit.DIAMONDS, 4), new Card(Suit.SPADES, 3)));
		Move two = new Move(new Pile(PileType.TABLEAU), new Pile(PileType.TABLEAU), new Card(Suit.CLUBS, 5));
		two.withFollowers(Arrays.asList(new Card(Suit.DIAMONDS, 4), new Card(Suit.SPADES, 3), new Card(Suit.DIAMONDS, 2)));

		Assert.assertNotEquals(one, two);
	}
}
