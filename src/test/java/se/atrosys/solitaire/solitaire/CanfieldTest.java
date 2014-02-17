package se.atrosys.solitaire.solitaire;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.moves.Move;
import se.atrosys.solitaire.cardstuff.Suit;
import se.atrosys.solitaire.cardstuff.piles.IneligibleCardException;

import java.util.List;

public class CanfieldTest {
	Canfield canfield;

	@BeforeMethod
	public void setup() throws Exception {
		 canfield = new Canfield();
	}

	@Test
	public void availableMovesShouldNotBeNull() {
		List<Move> moves = canfield.getAvailableMoves();

		Assert.assertNotNull(moves);
	}

	@Test
	public void tableauMovesShouldWork() throws IneligibleCardException {
		for (int i = 0 ; i < 4 ; i++) {
			canfield.getTableaux().get(i).clear();
		}

		canfield.getTableaux().get(0).addCard(new Card(Suit.CLUBS, 5));
		canfield.getTableaux().get(1).addCard(new Card(Suit.DIAMONDS, 6));
		canfield.getTableaux().get(2).addCard(new Card(Suit.SPADES, 9));
		canfield.getTableaux().get(3).addCard(new Card(Suit.HEARTS, 10));

		List<Move> moves = canfield.getTableauInternalMoves();

		Assert.assertNotNull(moves);
		Assert.assertEquals(2, moves.size());
	}
}
