package se.atrosys.solitaire.card.pile;

import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.pile.rule.Rule;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Pile pile = (Pile) o;

        if (pileType != pile.pileType) { return false; }

		// this is so we avoid problems setting up, because the Set will reject adds otherwise
		if (cards.isEmpty() && pile.cards.isEmpty() && !name.equals(pile.name)) { return false; }

        if (pileType.isOrdered()) {
            if (!cards.equals(pile.cards)) { return false; }
        } else {
            if (!cards.containsAll(pile.cards)) { return false; }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = pileType.hashCode();

		if (!pileType.isOrdered()) {
			Set<Card> set = new HashSet<>();
			set.addAll(cards);
			result = 31 * result + set.hashCode();
		} else {
			result = 31 * result + cards.hashCode();
		}
		
        return result;
    }

	public int size() {
		return cards.size();
	}

	public boolean isEmpty() {
		return cards.isEmpty();
	}
}
