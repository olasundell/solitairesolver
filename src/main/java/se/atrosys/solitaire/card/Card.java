package se.atrosys.solitaire.card;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class Card implements Comparable<Card> {
	private final Suit suit;
	private final int rank;

	public Card(Suit suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Card(String s) {
		if (s.length() != 2) {
			throw new InputMismatchException(String.format("%s is not a valid card!", s));
		}

		switch (s.charAt(0)) {
		case 'A':
			rank = 1;
			break;
		case 'K':
			rank = 13;
			break;
		case 'Q':
			rank = 12;
			break;
		case 'J':
			rank = 11;
			break;
		case 'T':
			rank = 10;
			break;
		default:
			rank = Character.getNumericValue(s.charAt(0));

			if (rank > 13 || rank < 1) {
				throw new InputMismatchException(String.format("%s is not a valid card!", s));
			}

			break;
		}

		switch (s.charAt(1)) {
		case 'S':
			suit = Suit.SPADES;
			break;
		case 'H':
			suit = Suit.HEARTS;
			break;
		case 'D':
			suit = Suit.DIAMONDS;
			break;
		case 'C':
			suit = Suit.CLUBS;
			break;
		default:
			throw new InputMismatchException(String.format("%s is not a valid card!", s));
		}
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
			break;
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
	public int hashCode() {
		int result = suit.hashCode();
		result = 31 * result + rank;
		return result;
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
