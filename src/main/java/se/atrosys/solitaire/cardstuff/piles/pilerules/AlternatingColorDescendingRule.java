package se.atrosys.solitaire.cardstuff.piles.pilerules;

import se.atrosys.solitaire.cardstuff.Card;

public class AlternatingColorDescendingRule implements Rule {
	@Override
	public boolean eligible(Card existing, Card intended) {
		// TODO this should be handled more gracefully
		// anything goes if the pile is empty
		if (existing == null) {
			return true;
		}

		if (intended == null) {
			return false;
		}

		if (existing.getColor() == intended.getColor()) {
			return false;
		}

		if (existing.getRank() != (intended.getRank() + 1)) {
			return false;
		}

		return true;
	}
}
