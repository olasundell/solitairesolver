package se.atrosys.solitaire.cardstuff.piles.pilerules;

import se.atrosys.solitaire.cardstuff.Card;

public interface Rule {
	public boolean eligible(Card existing, Card intended);
}
