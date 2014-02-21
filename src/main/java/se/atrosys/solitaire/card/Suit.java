package se.atrosys.solitaire.card;

public enum Suit {
	SPADES(Color.BLACK),
	HEARTS(Color.RED),
	DIAMONDS(Color.RED),
	CLUBS(Color.BLACK);

	private final Color color;

	Suit(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public char shortName() {
		return this.name().charAt(0);
	}
}
