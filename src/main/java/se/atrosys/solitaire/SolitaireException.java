package se.atrosys.solitaire;

public class SolitaireException extends Throwable {
	protected SolitaireException(String s) {
		super(s);
	}

	protected SolitaireException(String s, Throwable t) {
		super(s, t);
	}
}
