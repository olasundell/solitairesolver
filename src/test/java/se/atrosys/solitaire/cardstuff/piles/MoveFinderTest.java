package se.atrosys.solitaire.cardstuff.piles;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.moves.Move;
import se.atrosys.solitaire.cardstuff.Suit;
import se.atrosys.solitaire.cardstuff.moves.MoveFinder;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;

import java.util.List;

public class MoveFinderTest {
	MoveFinder moveFinder;

	@BeforeMethod
	public void setUp() throws Exception {
		moveFinder = new MoveFinder();
	}

	@Test
	public void movesShouldBeEmptyWithNoCards() {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertNotNull(moves);
		Assert.assertTrue(moves.isEmpty());
	}

	@Test
	public void shouldFindSimpleMove() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.CLUBS, 4);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		second.addCard(secondCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(secondCard, move.getCard());
		Assert.assertEquals(second, move.getFrom());
		Assert.assertEquals(first, move.getTo());
	}

	@Test
	public void takeOnlyShouldBeHonored() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule()).withTakeOnly();
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.CLUBS, 4);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		second.addCard(secondCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(0, moves.size());
//		Move move = moves.get(0);
//		Assert.assertEquals(secondCard, move.getCard());
//		Assert.assertEquals(second, move.getFrom());
//		Assert.assertEquals(first, move.getTo());
	}

	@Test
	public void shouldFindSimpleReversedMove() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.DIAMONDS, 3);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.CLUBS, 4);
		second.addCard(secondCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(firstCard, move.getCard());
		Assert.assertEquals(first, move.getFrom());
		Assert.assertEquals(second, move.getTo());
	}

	@Test
	public void shouldFindSlightlyMoreComplexCase() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.CLUBS, 4);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		first.addCard(secondCard);

		Card thirdCard = new Card(Suit.SPADES, 2);
		second.addCard(thirdCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(thirdCard, move.getCard());
		Assert.assertEquals(second, move.getFrom());
		Assert.assertEquals(first, move.getTo());
	}

	@Test
	public void shouldFindDeepMove() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.CLUBS, 4);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		first.addCard(secondCard);
		Card thirdCard = new Card(Suit.SPADES, 2);
		first.addCard(thirdCard);

		Card fourthCard = new Card(Suit.SPADES, 4);
		second.addCard(fourthCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(secondCard, move.getCard());
		Assert.assertEquals(first, move.getFrom());
		Assert.assertEquals(second, move.getTo());
		Assert.assertEquals(1, move.getFollowers().size());
		Assert.assertEquals(thirdCard, move.getFollowers().get(0));
	}

	@Test
	public void shouldFindDeepMoveOtherWay() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new AlternatingColorDescendingRule());

		Card firstCard = new Card(Suit.CLUBS, 4);
		second.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		second.addCard(secondCard);
		Card thirdCard = new Card(Suit.SPADES, 2);
		second.addCard(thirdCard);

		Card fourthCard = new Card(Suit.SPADES, 4);
		first.addCard(fourthCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(secondCard, move.getCard());
		Assert.assertEquals(second, move.getFrom());
		Assert.assertEquals(first, move.getTo());
		Assert.assertEquals(1, move.getFollowers().size());
		Assert.assertEquals(thirdCard, move.getFollowers().get(0));
	}

	@Test
	public void moveWithDifferentRulesAndTopOnly() throws IneligibleCardException {
		Pile first = new Pile().withRule(new AlternatingColorDescendingRule());
		Pile second = new Pile().withRule(new SameSuitAscendingAceFirstRule()).withTopOnly();

		Card firstCard = new Card(Suit.CLUBS, 4);
		first.addCard(firstCard);
		Card secondCard = new Card(Suit.DIAMONDS, 3);
		first.addCard(secondCard);
		Card thirdCard = new Card(Suit.SPADES, 2);
		first.addCard(thirdCard);

		Card fourthCard = new Card(Suit.SPADES, 1);
		second.addCard(fourthCard);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
		Move move = moves.get(0);
		Assert.assertEquals(thirdCard, move.getCard());
		Assert.assertEquals(first, move.getFrom());
		Assert.assertEquals(second, move.getTo());
		Assert.assertEquals(0, move.getFollowers().size());
	}
}
