package se.atrosys.solitaire.game;

import se.atrosys.solitaire.card.move.IllegalMoveException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.pile.IneligibleCardException;

public interface Solitaire {
	String hashString();

	void executeMove(Move move) throws IneligibleCardException, IllegalMoveException;
}
