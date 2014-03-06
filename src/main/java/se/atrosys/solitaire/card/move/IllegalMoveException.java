package se.atrosys.solitaire.card.move;

import se.atrosys.solitaire.SolitaireException;

public class IllegalMoveException extends SolitaireException {
	public IllegalMoveException(String s, Move move) {
		super(String.format("%s, move is %s", s, move.toString()));
	}
}
