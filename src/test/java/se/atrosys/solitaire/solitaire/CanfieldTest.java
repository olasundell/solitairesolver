package se.atrosys.solitaire.solitaire;

import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.atrosys.solitaire.cardstuff.Move;

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
	public void availableMovesShouldWork() {
		List<Move> moves = canfield.getAvailableMoves();
		Assert.assertNotSame(0, moves.size());
	}

	@Test
	public void tableauMovesShouldWork() {
		List<Move> moves = canfield.getTableauMoves();

		Assert.assertNotNull(moves);
		Assert.assertNotSame(0, moves.size());
	}
}
