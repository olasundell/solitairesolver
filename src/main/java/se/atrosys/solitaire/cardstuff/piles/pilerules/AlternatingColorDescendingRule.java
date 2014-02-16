package se.atrosys.solitaire.cardstuff.piles.pilerules;

import se.atrosys.solitaire.cardstuff.Card;

public class AlternatingColorDescendingRule implements Rule {
	@Override
	public boolean eligible(Card existing, Card intended) {
		if (existing.getColor() == intended.getColor()) {
			return false;
		}

		if (existing.getRank() != (intended.getRank() + 1)) {
			return false;
		}

		return true;
	}
}
