package se.atrosys.solitaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.game.Canfield;
import se.atrosys.solitaire.game.CanfieldBuilder;

import java.util.Set;

public class Game {
	public Game() {
	}

	public void playGame() throws EmptyDeckException {
		Canfield canfield = new CanfieldBuilder().setRandseed(0).createCanfield();
		NTree nTree = new NTree();

		for (int i = 0 ; i < 3 ; i++) {
			Set<Move> moves = canfield.getAvailableMoves();
			nTree.add(null);
		}
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Game.class);

		try {
			new Game().playGame();
		} catch (EmptyDeckException e) {
			logger.error("Ooops", e);
		}
	}
}
