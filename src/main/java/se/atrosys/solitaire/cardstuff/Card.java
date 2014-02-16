package se.atrosys.solitaire.cardstuff;

public class Card {
	private final Suit suit;
	private final int rank;

	public Card(Suit suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(suit.shortName());

		switch (rank) {
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
}
