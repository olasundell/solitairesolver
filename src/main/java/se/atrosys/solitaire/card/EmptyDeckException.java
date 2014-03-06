package se.atrosys.solitaire.card;

import se.atrosys.solitaire.SolitaireException;

public class EmptyDeckException extends SolitaireException {
	public EmptyDeckException(String s) {
		super(s);
	}
}
