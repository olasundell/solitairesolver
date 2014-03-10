package se.atrosys.solitaire.card.pile;

import se.atrosys.solitaire.SolitaireException;

public class IneligibleCardException extends SolitaireException {
	public IneligibleCardException(String s) {
		super(s);
	}

	public IneligibleCardException(String s, IneligibleCardException e) {
		super(s, e);
	}
}
