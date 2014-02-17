package se.atrosys.solitaire.cardstuff.piles;

import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.EmptyDeckException;
import se.atrosys.solitaire.cardstuff.piles.pilerules.NoRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {
	private final List<Card> cards;
	private Rule rule;
	private boolean topOnly;
	private boolean takeOnly;

	public Pile() {
		cards = new ArrayList<>();
		rule = new NoRule();
		topOnly = false;
		takeOnly = false;
	}

	public Pile withRule(Rule rule) {
		this.rule = rule;

		return this;
	}

	public Pile withTakeOnly() {
		takeOnly = true;

		return this;
	}

	public Pile withTopOnly() {
		topOnly = true;

		return this;
	}

	public void addCards(List<Card> several) {
		cards.addAll(several);
	}

	public Card peek() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.get(cards.size() -1);
	}

	public Card take() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.remove(cards.size() -1);
	}

	public void addCard(Card card) throws IneligibleCardException {
		if (rule.eligible(this.peek(), card)) {
			cards.add(card);
		} else {
			throw new IneligibleCardException(String.format("Tried to add %s to %s, pile is %s", card, this.peek(), this));
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Card card: cards) {
			builder.append(card.toString());
			builder.append(" ");
		}

		return builder.toString();
	}

	public void clear() {
		cards.clear();
	}

	public Rule getRule() {
		return rule;
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	public boolean isTopOnly() {
		return topOnly;
	}

	public boolean isTakeOnly() {
		return takeOnly;
	}
}
