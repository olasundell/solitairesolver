package se.atrosys.solitaire.cardstuff.piles.pilerules;

import se.atrosys.solitaire.cardstuff.Card;

public class TakeOnlyRule implements Rule {
	@Override
	public boolean eligible(Card existing, Card intended) {
		return false;
	}
}
