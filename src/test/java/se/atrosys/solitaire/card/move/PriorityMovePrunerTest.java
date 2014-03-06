package se.atrosys.solitaire.card.move;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;
import se.atrosys.solitaire.game.Canfield;
import se.atrosys.solitaire.game.CanfieldBuilder;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PriorityMovePrunerTest {
	Canfield canfield;
	Set<Move> moves;
	private PriorityMovePruner pruner;

	@BeforeMethod
	public void setup() throws EmptyDeckException {
		canfield = new CanfieldBuilder().setRandseed(0).createCanfield();
		moves = canfield.getNormalMoves();
		pruner = new PriorityMovePruner();
	}

	@Test
	public void shouldPruneMoves() {
		Set<Move> prunedMoves = pruner.pruneMoves(moves, 0);

		Assert.assertNotNull(prunedMoves);
		Assert.assertEquals(prunedMoves.size(), 1);
	}

	@Test
	public void foundationMovePruningShouldReturnEmptyGivenNonFoundationMove() {
		Move move = new Move(new Pile(PileType.RESERVE), new Pile(PileType.TABLEAU), new Card(Suit.DIAMONDS, 3));
		Set<Move> result = pruner.pruneFoundation(move);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 0);
	}

	@Test
	public void foundationMovePruningShouldReturnStuffGivenFoundationMove() {
		Move move = new Move(new Pile(PileType.RESERVE), new Pile(PileType.FOUNDATION), new Card(Suit.DIAMONDS, 3));
		Set<Move> result = pruner.pruneFoundation(move);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void shouldPruneAceToMultipleEmptyFoundations() {
		Set<Move> multi = new LinkedHashSet<>();

		Card aceOfClubs = new Card(Suit.CLUBS, 1);
		Card aceOfSpades = new Card(Suit.SPADES, 1);

		Pile stock = new Pile(PileType.STOCK);

		Pile foundation1 = new Pile(PileType.FOUNDATION).withName("foundation1");
		Pile foundation2 = new Pile(PileType.FOUNDATION).withName("foundation2");
		Pile foundation3 = new Pile(PileType.FOUNDATION).withName("foundation3");

		multi.add(new Move(stock, foundation1, aceOfClubs));
		multi.add(new Move(stock, foundation2, aceOfClubs));
		multi.add(new Move(stock, foundation3, aceOfClubs));
		multi.add(new Move(stock, foundation1, aceOfSpades));
		multi.add(new Move(stock, foundation2, aceOfSpades));
		multi.add(new Move(stock, foundation3, aceOfSpades));
		multi.add(new Move(stock, new Pile(PileType.FOUNDATION).withName("foundation4"), new Card(Suit.HEARTS, 3)));

		Set<Move> result = pruner.pruneAcesToFoundations(multi);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.size(), 3);
	}

	@Test
	public void fromReserveShouldAlwaysBePrio() {
		Set<Move> rawMoves = new HashSet<>();
		rawMoves.add(new Move(new Pile(PileType.RESERVE), new Pile(PileType.TABLEAU), new Card(Suit.HEARTS, 3)));
		Set<Move> pruned = pruner.pruneMoves(rawMoves, 0);
		Assert.assertEquals(pruned.size(), 1);
	}

	@Test
	public void shouldPruneUsingMinimumFoundationRank() {
		Set<Move> multi = new LinkedHashSet<>();

		Card aceOfClubs = new Card(Suit.CLUBS, 1);
		Card aceOfSpades = new Card(Suit.SPADES, 1);
		Card twoOfClubs = new Card(Suit.CLUBS, 2);
		Card twoOfSpades = new Card(Suit.SPADES, 2);

		Pile stock = new Pile(PileType.STOCK);

		Pile foundation1 = new Pile(PileType.FOUNDATION).withName("foundation1");
		Pile foundation2 = new Pile(PileType.FOUNDATION).withName("foundation2");
		Pile foundation3 = new Pile(PileType.FOUNDATION).withName("foundation3");

		foundation1.dealCard(aceOfClubs);
		foundation2.dealCard(aceOfSpades);
		foundation3.dealCard(new Card(Suit.HEARTS, 1));
		foundation3.dealCard(new Card(Suit.HEARTS, 2));
		foundation3.dealCard(new Card(Suit.HEARTS, 3));
		foundation3.dealCard(new Card(Suit.HEARTS, 4));

		multi.add(new Move(stock, foundation1, twoOfClubs));
		multi.add(new Move(stock, foundation2, twoOfSpades));
		multi.add(new Move(stock, foundation3, new Card(Suit.HEARTS, 5)));

		Set<Move> prunedMoves = pruner.pruneByMinimumFoundationRank(multi, 0);

		Assert.assertNotNull(prunedMoves);
		Assert.assertEquals(prunedMoves.size(), 2);

	}
}
