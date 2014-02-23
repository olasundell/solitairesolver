package se.atrosys.solitaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.game.Canfield;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.ErrorManager;

public class Game {
	public Game() {
	}

	public void playGame() throws EmptyDeckException {
		Canfield canfield = new Canfield();
		NTree nTree = new NTree();

		for (int i = 0 ; i < 3 ; i++) {
			Set<Move> moves = canfield.getAvailableMoves();
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
