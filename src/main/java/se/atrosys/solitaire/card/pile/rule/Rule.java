package se.atrosys.solitaire.card.pile.rule;

import se.atrosys.solitaire.card.Card;

public interface Rule {
	public boolean eligible(Card existing, Card intended);
}
