package se.atrosys.solitaire.card.pile.rule;

import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.Suit;
import se.atrosys.solitaire.card.pile.Pile;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PileComparator implements Comparator<Pile> {
	@Override
	public int compare(Pile o1, Pile o2) {
		if (o1.equals(o2)) {
			return 0;
		}

		List<Card> o1Cards = o1.getCards();
		List<Card> o2Cards = o2.getCards();

		if (o1Cards.isEmpty()) {
			return -1;
		} else if (o2Cards.isEmpty()) {
			return 1;
		}

		if (o1Cards.size() == o2Cards.size()) {
			Card o1C = o1Cards.get(0);
			Card o2C = o2Cards.get(0);

			if (o1C.getSuit() == o2C.getSuit()) {
				return o1C.getRank() - o2C.getRank();
			}

			List<Suit> suits = Arrays.asList(Suit.values());

			return suits.indexOf(o1C.getSuit()) - suits.indexOf(o2C.getSuit());
		} else {
			return o1Cards.size() - o2Cards.size();
		}
	}
}
