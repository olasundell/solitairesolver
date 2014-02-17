package se.atrosys.solitaire.cardstuff.piles;

import se.atrosys.solitaire.cardstuff.Card;
import se.atrosys.solitaire.cardstuff.Move;
import se.atrosys.solitaire.cardstuff.piles.pilerules.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveFinder {
	public List<Move> getMovesFromPiles(Pile firstPile, Pile secondPile) {
		List<Card> firstPileCards = firstPile.getCards();
		List<Card> secondPileCards = secondPile.getCards();
		Rule rule = firstPile.getRule();
		Set<Move> moves = new HashSet<Move>();


		// TODO this needs a generalisation rewrite, code reuse is deplorable.
		// TODO this code generates a whole lot of duplicates, which is rather inelegantly handled by the Set we're using.

		for (int k = 0; k < firstPileCards.size(); k++) {
			Card firstCard = firstPileCards.get(k);
			Card secondCard = secondPile.peek();

//			if (rule.eligible(firstCard, secondCard)) {
//				List<Card> followers = secondPileCards.subList(k + 1, secondPileCards.size());
//				moves.add(new Move(secondPile, firstPile, secondCard).withFollowers(followers));
//			}

			if (rule.eligible(secondCard, firstCard)) {
				List<Card> followers = firstPileCards.subList(k + 1, firstPileCards.size());
				moves.add(new Move(firstPile, secondPile, firstCard).withFollowers(followers));
			}
		}

		for (int k = 0 ; k < secondPileCards.size() ; k++) {
			Card firstCard = firstPile.peek();
			Card secondCard = secondPileCards.get(k);

			if (rule.eligible(firstCard, secondCard)) {
				List<Card> followers = secondPileCards.subList(k + 1, secondPileCards.size());
				moves.add(new Move(secondPile, firstPile, secondCard).withFollowers(followers));
			}

//			if (rule.eligible(secondCard, firstCard)) {
//				List<Card> followers = firstPileCards.subList(k, firstPileCards.size());
//				moves.add(new Move(firstPile, secondPile, firstCard).withFollowers(followers));
//			}
		}

		return new ArrayList<Move>(moves);
	}
}