package se.atrosys.solitaire.cardstuff.piles;

import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;

import java.util.ArrayList;
import java.util.List;

public class Pile {
	private final List<Card> cards;
	private Rule rule;

	public Pile() {
		cards = new ArrayList<>();
	}

	public Pile withRule(Rule rule) {
		this.rule = rule;

		return this;
	}

	public void addCards(List<Card> several) {
		cards.addAll(several);
	}

	private Card peek() {
		return cards.get(0);
	}

	private Card take() {
		return cards.remove(0);
	}

	public void addCard(Card card) {
		// you can always add a card if the pile is empty.
		if (rule.eligible(this.peek(), card)) {
			cards.add(card);
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
}
