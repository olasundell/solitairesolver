package se.atrosys.solitaire.game;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.move.IllegalMoveException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.IneligibleCardException;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;
import se.atrosys.solitaire.card.pile.rule.PileComparator;

import java.util.*;

public class CanfieldTest {
	Canfield canfield;
	private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@BeforeMethod
	public void setup() throws Exception {
		 canfield = new Canfield();
	}

	@Test
	public void availableMovesShouldNotBeNull() {
		Set<Move> moves = canfield.getAvailableMoves();

		Assert.assertNotNull(moves);
		for (Move move: moves) {
			Assert.assertTrue(move.getTo().getRule().eligible(move.getTo().peek(), move.getCard()));
		}
	}

	@Test
	public void tableauInternalMovesShouldWork() throws IneligibleCardException {
		Pile[] tableaux = canfield.getTableaux().toArray(new Pile[canfield.getTableaux().size()]);

		for (int i = 0 ; i < 4 ; i++) {
			tableaux[i].clear();
		}

		tableaux[0].addCard(new Card(Suit.CLUBS, 5));
		tableaux[1].addCard(new Card(Suit.DIAMONDS, 6));
		tableaux[2].addCard(new Card(Suit.SPADES, 9));
		tableaux[3].addCard(new Card(Suit.HEARTS, 10));

		Set<Move> moves = canfield.getTableauInternalMoves();

		Assert.assertNotNull(moves);
		Assert.assertEquals(2, moves.size());
	}

	@Test(enabled = false)
	public void shouldPruneFoundationMoves() {
		// We know we'll only have one ace to move to an empty foundation.
		Set<Move> moves = canfield.getAvailableMoves();

		Set<Move> prunedMoves = canfield.pruneMovesToFoundations(moves);

		Assert.assertNotNull(prunedMoves);
		Assert.assertFalse(prunedMoves.isEmpty());

		int numberOfFoundationMoves = 0;

		for (Move move: prunedMoves) {
			for (Pile foundation: canfield.getFoundations()) {
				if (move.getTo().equals(foundation)) {
					numberOfFoundationMoves++;
				}
			}
		}

		Assert.assertEquals(1, numberOfFoundationMoves);
	}

	@Test
	public void shouldOnlyFindOneMoveFromReserveToTableauxWhenTableauxEmpty() {
		Deque<Pile> tableaux = canfield.getTableaux();
		Pile[] tabArr = tableaux.toArray(new Pile[tableaux.size()]);

		// TODO this puts the whole solitaire in a broken state, find a better way to generate an empty tableaux
		tabArr[0].clear();

		Set<Move> moves = canfield.getAvailableMoves();

		Assert.assertEquals(moves.size(), 1);
	}

	@Test
	public void shouldNotReportAsSolvedWhenInFactNotSolved() {
		Assert.assertFalse(canfield.isSolved());
	}

	@Test
	public void shouldReportAsSolvedWhenActuallySolved() {
		Pile[] foundations = canfield.getFoundations().toArray(new Pile[4]);
		for (int i = 0 ; i < 4 ; i++) {
			foundations[i].clear();
			for (int j = 1 ; j <= 13 ; j++) {
				foundations[i].dealCard(new Card(Suit.values()[i], j));
			}
		}

		Assert.assertTrue(canfield.isSolved());
	}

	@Test
	public void hashCodeShouldBeSameEvenIfStockChangesOrder() {
		int one = canfield.hashCode();
		Pile stock = canfield.getStock();
		Card c1 = stock.take();
		Card c2 = stock.take();

		stock.dealCard(c1);
		stock.dealCard(c2);

		int two = canfield.hashCode();

		Assert.assertEquals(one, two);
	}

	@Test(enabled = false)
	public void hashCodesShouldBeUnique() throws EmptyDeckException {
		Map<String, Canfield> map = new HashMap<>();
		Set<String> set = new HashSet<>();
		int duplicates = 0;

		for (long i = 0 ; i < 1000000 ; i++) {
			Canfield c = new Canfield(i);
			String key = c.hashString();

			if (i % 10000 == 0) {
				System.out.println(i);
			}

			if (set.contains(key)) {
				Canfield expected = map.get(key);
				Assert.assertEquals(expected,
						c,
						String.format("iteration %d: map contains %s but %s isn't equal.", i, expected.toString(), c.toString()));

				duplicates++;
			}
			map.put(key, c);
			set.add(key);
		}

		System.out.println("Duplicates: " + duplicates);
	}

	@Test(expectedExceptions = IllegalMoveException.class)
	public void assertMoveShouldWorkWithNullFrom() throws IllegalMoveException {
		Move move = new Move(null, canfield.getStock(), canfield.getStock().peek());
		canfield.assertMove(move);
	}

	@Test(expectedExceptions = IllegalMoveException.class)
	public void assertMoveShouldWorkWithNullTo() throws IllegalMoveException {
		Move move = new Move(canfield.getStock(), null, canfield.getStock().peek());
		canfield.assertMove(move);
	}

	@Test(expectedExceptions = IllegalMoveException.class)
	public void assertMoveShouldWorkWithNullCard() throws IllegalMoveException {
		Move move = new Move(canfield.getStock(), null, canfield.getStock().peek());
		canfield.assertMove(move);
	}

	@Test(expectedExceptions = IllegalMoveException.class)
	public void assertMoveShouldWorkWithNonExistingFrom() throws IllegalMoveException {
		Move move = new Move(new Pile(PileType.FOUNDATION), canfield.getStock(), canfield.getStock().peek());
		canfield.assertMove(move);
	}

	@Test
	public void executeMove() throws IneligibleCardException, IllegalMoveException {
		Set<Move> moves = canfield.getAvailableMoves();
		Move move = moves.iterator().next();

		canfield.executeMove(move);
	}

	@Test
	public void moveAndUndoShouldResultInOriginalState() throws IneligibleCardException, IllegalMoveException {
		String firstHash = canfield.hashString();
		Set<Move> moves = canfield.getAvailableMoves();
		Move move = moves.iterator().next();

		canfield.executeMove(move);
		Assert.assertNotEquals(canfield.hashString(), firstHash);

		canfield.undoLatest();
		Assert.assertEquals(canfield.hashString(), firstHash);
	}
}
