package se.atrosys.solitaire.card.pile;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.move.MoveFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MoveFinderTest {
	private MoveFinder moveFinder;
	private Pile first;
	private Pile second;

	@BeforeMethod
	public void setUp() throws Exception {
		moveFinder = new MoveFinder();
		first = new Pile(PileType.TABLEAU);
		second = new Pile(PileType.TABLEAU);
	}

	@Test
	public void movesShouldBeEmptyWithNoCards() {
		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertNotNull(moves);
		Assert.assertTrue(moves.isEmpty());
	}

	@Test
	public void shouldFindSimpleMove() throws IneligibleCardException {
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
	public void shouldFindSimpleReversedMove() throws IneligibleCardException {
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
		second = new Pile(PileType.FOUNDATION);

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

	@Test
	public void moveWithTakeOnlyAndNoTopOnly() {
		second = new Pile(PileType.RESERVE);

		first.dealCard(new Card(Suit.CLUBS, 10));
		second.dealCards(Arrays.asList(new Card(Suit.CLUBS, 9), new Card(Suit.HEARTS, 8), new Card(Suit.DIAMONDS, 9)));

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);

		Assert.assertEquals(1, moves.size());
	}

	@Test
	public void shouldFindMoveToFoundation() {
		first = new Pile(PileType.STOCK);
		second = new Pile(PileType.FOUNDATION);

		Card card = new Card(Suit.CLUBS, 1);
		first.dealCard(card);

		List<Move> moves = moveFinder.getMovesFromPiles(first, second);
		Assert.assertEquals(moves.size(), 1);
		Assert.assertEquals(moves.get(0).getCard(), card);
		Assert.assertEquals(moves.get(0).getFrom(), first);
		Assert.assertEquals(moves.get(0).getTo(), second);
	}

	@Test
	public void shouldBeTopOnlyToFoundation() {
		first = new Pile(PileType.TABLEAU);
		first.dealCard(new Card("4H"));
		first.dealCard(new Card("3S"));

		second = new Pile(PileType.FOUNDATION);
		second.dealCard(new Card("AH"));
		second.dealCard(new Card("2H"));
		second.dealCard(new Card("3H"));

		Assert.assertEquals(moveFinder.getMovesFromPiles(first, second).size(), 0);
		Assert.assertEquals(moveFinder.getMovesFromPiles(second, first).size(), 0);
	}

	@Test
	public void shouldFindAllMovesFromStockToFoundation() {
		Pile stock = new Pile(PileType.STOCK);
		Pile foundation = new Pile(PileType.FOUNDATION);

		// this is the stock we get with randseed 2
		String[] c = {"3S", "4S", "5S", "6S", "8S", "AH", "2H", "4H", "5H", "6H", "8H", "9H", "TH", "QH", "KH", "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "JD", "QD", "KD", "AC", "2C", "7C", "TC", "QC"};

		for (String s: c) {
			stock.dealCard(new Card(s));
		}


		List<Move> moves = moveFinder.getMovesFromPiles(stock, foundation);

		Assert.assertNotNull(moves);
		Assert.assertNotEquals(moves.size(), 0, "There should be moves, damnit.");
	}
}
