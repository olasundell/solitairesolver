package se.atrosys.solitaire.cardstuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	final List<Card> cards;
	private Random random = new Random(0);

	public Deck() {
		cards = new ArrayList<>();

		for (Suit suit: Suit.values()) {
			for (int i = 1 ; i <= 13 ; i++) {
				cards.add(new Card(suit, i));
			}
		}
	}

	public Card getNext() throws EmptyDeckException {
		if (cards.isEmpty()) {
			throw new EmptyDeckException();
		}

		return cards.remove(random.nextInt(cards.size()));
	}

	protected void setRandom(Random random) {
		this.random = random;
	}

	public List<Card> getSeveral(int num) throws EmptyDeckException {
		List<Card> list = new ArrayList<>();
		for (int i = 0 ; i < num ; i++) {
			list.add(getNext());
		}

		return list;
	}
}
