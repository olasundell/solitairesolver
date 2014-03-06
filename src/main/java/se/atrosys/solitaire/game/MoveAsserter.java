package se.atrosys.solitaire.game;

import se.atrosys.solitaire.card.move.IllegalMoveException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.pile.Pile;

public class MoveAsserter {
	private final Canfield canfield;

	public MoveAsserter(Canfield canfield) {
		this.canfield = canfield;
	}

	protected void assertMove(Move move) throws IllegalMoveException {
		Pile from = move.getFrom();
		Pile to = move.getTo();

		if (from == null) {
			throw new IllegalMoveException("From is null!", move);
		}

		if (to == null) {
			throw new IllegalMoveException("To is null!", move);
		}

		if (move.getCard() == null) {
			throw new IllegalMoveException("Card is null!", move);
		}

		if (!from.getCards().contains(move.getCard())) {
			throw new IllegalMoveException("Pile which we're about to move from does not contain intended card!", move);
		}

		if (!canfield.getFoundations().contains(from) &&
				!canfield.getTableaux().contains(from) &&
				canfield.getStock() != from &&
				canfield.getReserve() != from) {
			throw new IllegalMoveException("From pile does not exist in current solitaire!", move);
		}

		if (!canfield.getFoundations().contains(to) &&
				!canfield.getTableaux().contains(to) &&
				canfield.getStock() != to &&
				canfield.getReserve() != to) {
			throw new IllegalMoveException("To pile does not exist in current solitaire!", move);
		}
	}
}