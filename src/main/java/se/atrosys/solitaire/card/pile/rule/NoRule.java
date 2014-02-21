package se.atrosys.solitaire.card.pile.rule;

import se.atrosys.solitaire.card.Card;

public class NoRule implements Rule {
	@Override
	public boolean eligible(Card existing, Card intended) {
		return true;
	}
}
