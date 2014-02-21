package se.atrosys.solitaire.card.pile.rule;

import se.atrosys.solitaire.card.Card;

public class SameSuitAscendingAceFirstRule implements Rule {
	@Override
	public boolean eligible(Card existing, Card intended) {
		// TODO this should be handled more gracefully
		// we only accept aces if the pile is empty
		if (existing == null) {
			return intended.getRank() == 1;
		}

		if (existing.getSuit() != intended.getSuit()) {
			return false;
		}

		if (existing.getRank() != intended.getRank() - 1) {
			return false;
		}

		return true;
	}
}
