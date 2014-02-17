package se.atrosys.solitaire.cardstuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	final List<Card> cards = new ArrayList<>();
	private Random random = new Random(0);

	public Deck() {
		for (Suit suit: Suit.values()) {
			for (int i = 1 ; i <= 13 ; i++) {
				cards.add(new Card(suit, i));
			}
		}
	}

	public Card takeNext() throws EmptyDeckException {
		if (cards.isEmpty()) {
			throw new EmptyDeckException();
		}

		return cards.remove(random.nextInt(cards.size()));
	}

	protected void setRandom(Random random) {
		this.random = random;
	}

	public List<Card> takeSeveral(int num) throws EmptyDeckException {
		List<Card> list = new ArrayList<>();
		for (int i = 0 ; i < num ; i++) {
			list.add(takeNext());
		}

		return list;
	}


	public List<Card> takeRemaining() {
		List<Card> list = new ArrayList<>();
		list.addAll(cards.subList(0, cards.size()));

		cards.clear();

		return list;
	}
}
