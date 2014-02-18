package se.atrosys.solitaire.cardstuff.moves;

import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.piles.Pile;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveFinder {
	public List<Move> getMovesFromPiles(Pile firstPile, Pile secondPile) {
		Set<Move> moves = new HashSet<>();

		// TODO this needs a generalisation rewrite, code reuse is deplorable.
		// TODO this code generates a whole lot of duplicates, which is rather inelegantly handled by the Set we're using.

		// FIXME this -must- handle Pile.topOnly()
		if (firstPile.isTopOnly() || secondPile.isTopOnly()) {
			moves.addAll(topMove(firstPile, secondPile));
			moves.addAll(topMove(secondPile, firstPile));
		} else {
			moves.addAll(iterate(firstPile, secondPile));
			moves.addAll(iterate(secondPile, firstPile));
		}

		return new ArrayList<>(moves);
	}

	protected List<Move> topMove(Pile firstPile, Pile secondPile) {
		ArrayList<Move> moves = new ArrayList<>();
		Card firstCard = firstPile.peek();
		Card secondCard = secondPile.peek();

		if (firstPile.getRule().eligible(firstCard, secondCard)) {
			moves.add(new Move(secondPile, firstPile, secondCard));
		}

		if (secondPile.getRule().eligible(secondCard, firstCard)) {
			moves.add(new Move(firstPile, secondPile, firstCard));
		}

		return moves;
	}

	protected Set<Move> iterate(Pile firstPile, Pile secondPile) {
		List<Card> firstPileCards = firstPile.getCards();
		Rule rule = secondPile.getRule();
		Set<Move> moves = new HashSet<>();

		for (int k = 0; k < firstPileCards.size(); k++) {
			Card firstCard = firstPileCards.get(k);
			Card secondCard = secondPile.peek();

			if (rule.eligible(secondCard, firstCard)) {
				List<Card> followers = firstPileCards.subList(k + 1, firstPileCards.size());
				moves.add(new Move(firstPile, secondPile, firstCard).withFollowers(followers));
			}
		}

		return moves;
	}
}