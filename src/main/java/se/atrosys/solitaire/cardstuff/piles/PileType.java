package se.atrosys.solitaire.cardstuff.piles;

import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.TakeOnlyRule;

public enum PileType {
	TABLEAU(new AlternatingColorDescendingRule(), TakeOrder.ALL),
	FOUNDATION(new SameSuitAscendingAceFirstRule(), TakeOrder.TOP_ONLY),
	RESERVE(new TakeOnlyRule(), TakeOrder.TOP_ONLY),
	STOCK(new TakeOnlyRule(), TakeOrder.ALL);

	private final Rule rule;
	private final TakeOrder takeOrder;

	PileType(Rule rule, TakeOrder takeOrder) {
		this.rule = rule;
		this.takeOrder = takeOrder;
	}

	public Rule getRule() {
		return rule;
	}

	public boolean isTopOnly() {
		return takeOrder.equals(TakeOrder.TOP_ONLY);
	}

	private enum TakeOrder {
		TOP_ONLY,
		ALL
	}
}
