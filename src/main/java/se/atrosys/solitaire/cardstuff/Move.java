package se.atrosys.solitaire.cardstuff;

import se.atrosys.solitaire.cardstuff.piles.Pile;

public class Move {
	private final Pile from;
	private final Pile to;
	private final Card card;

	public Move(Pile from, Pile to, Card card) {
		this.from = from;
		this.to = to;
		this.card = card;
	}
}
