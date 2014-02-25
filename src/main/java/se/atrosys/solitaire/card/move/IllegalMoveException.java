package se.atrosys.solitaire.card.move;

public class IllegalMoveException extends Throwable {
	public IllegalMoveException(String s, Move move) {
		super(String.format("%s, move is %s", s, move.toString()));
	}
}
