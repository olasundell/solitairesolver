package se.atrosys.solitaire.card.pile;

import se.atrosys.solitaire.card.pile.rule.AlternatingColorDescendingRule;
import se.atrosys.solitaire.card.pile.rule.Rule;
import se.atrosys.solitaire.card.pile.rule.SameSuitAscendingAceFirstRule;
import se.atrosys.solitaire.card.pile.rule.TakeOnlyRule;

public enum PileType {
	TABLEAU(new AlternatingColorDescendingRule(), TakeType.ALL, Order.ORDERED),
	FOUNDATION(new SameSuitAscendingAceFirstRule(), TakeType.TOP_ONLY, Order.ORDERED),
	RESERVE(new TakeOnlyRule(), TakeType.TOP_ONLY, Order.ORDERED),
	STOCK(new TakeOnlyRule(), TakeType.ALL, Order.UNORDERED);

	private final Rule rule;
	private final TakeType takeType;
    private final Order order;

	PileType(Rule rule, TakeType takeType, Order order) {
		this.rule = rule;
		this.takeType = takeType;
        this.order = order;
	}

	public Rule getRule() {
		return rule;
	}

	public boolean isTopOnly() {
		return takeType.equals(TakeType.TOP_ONLY);
	}

    public boolean isOrdered() {
        return order == Order.ORDERED;
    }

    private enum TakeType {
		TOP_ONLY,
		ALL
	}

    private enum Order {
        UNORDERED,
        ORDERED
    }
}
