package se.atrosys.solitaire.card;

import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card> {
	private final Suit suit;
	private final int rank;

	public Card(Suit suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		switch (rank) {
		case 1:
			builder.append('A');
			break;
		case 10:
			builder.append('T');
			break;
		case 11:
			builder.append('J');
			break;
		case 12:
			builder.append('Q');
			break;
		case 13:
			builder.append('K');
			break;
		default:
			builder.append(rank);
		}

		builder.append(suit.shortName());

		return builder.toString();
	}

	public Color getColor() {
		return suit.getColor();
	}

	public int getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (rank != card.rank) return false;
		if (suit != card.suit) return false;

		return true;
	}

	@Override
	public int compareTo(Card o) {
		if (getSuit() == o.getSuit()) {
			return getRank() - o.getRank();
		}

		List<Suit> suits = Arrays.asList(Suit.values());

		return suits.indexOf(o.getSuit()) - suits.indexOf(getSuit());
	}
}
