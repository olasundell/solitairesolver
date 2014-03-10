package se.atrosys.solitaire.card.pile.rule;

import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.Card;

public class SameSuitAscendingAceFirstRule implements Rule {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean eligible(Card existing, Card intended) {
		// TODO whoops, this shouldn't ever happen. Fix this further up the chain.
		if (intended == null) {
			logger.warn("Trying to place a null card");
			return false;
		}

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
