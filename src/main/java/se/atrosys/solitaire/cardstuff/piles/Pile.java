package se.atrosys.solitaire.cardstuff.piles;

import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.piles.pilerules.NoRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {
	private final PileType pileType;
	private final List<Card> cards;
	private String name;

	public Pile(PileType type) {
		pileType = type;
		cards = new ArrayList<>();
	}

	public Pile withName(String name) {
		this.name = name;

		return this;
	}

	public void addCards(List<Card> several) {
		cards.addAll(several);
	}

	public Card peek() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.get(cards.size() - 1);
	}

	public Card take() {
		if (cards.isEmpty()) {
			return null;
		}

		return cards.remove(cards.size() - 1);
	}

	public void dealCard(Card card) {
		cards.add(card);
	}

	public void addCard(Card card) throws IneligibleCardException {
		if (getRule().eligible(this.peek(), card)) {
			dealCard(card);
		} else {
			throw new IneligibleCardException(String.format("Tried to add %s to %s, pile is %s", card, this.peek(), this));
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (name != null && !name.isEmpty()) {
			builder.append(name);
		} else {
			for (Card card : cards) {
				builder.append(card.toString());
				builder.append(" ");
			}
		}

		return builder.toString();
	}

	public void clear() {
		cards.clear();
	}

	public Rule getRule() {
		return pileType.getRule();
	}

	public List<Card> getCards() {
		return Collections.unmodifiableList(cards);
	}

	public boolean isTopOnly() {
		return pileType.isTopOnly();
	}

	public void dealCards(List<Card> cards) {
		this.cards.addAll(cards);
	}
}
