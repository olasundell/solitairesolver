package se.atrosys.solitaire.game;

import org.junit.Assert;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.IneligibleCardException;
import se.atrosys.solitaire.card.pile.Pile;

import java.util.Set;

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

	@Test
	public void shouldPruneFoundationMoves() {
		// TODO write me
		Set<Move> moves = canfield.getAvailableMoves();

		Set<Move> prunedMoves = canfield.pruneMovesToFoundations(moves);

		Assert.assertNotNull(prunedMoves);
		Assert.assertFalse(prunedMoves.isEmpty());

		int numberOfFoundationMoves = 0;

		for (Move move: prunedMoves) {
//			if (move.getTo().)
//			numberOfFoundationMoves
		}
	}

	@Test
	public void shouldReportAsSolvedWhenActuallySolved() {
		// TODO write me.
	}
}
