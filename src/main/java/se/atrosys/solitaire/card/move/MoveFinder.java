package se.atrosys.solitaire.card.move;

import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;
import se.atrosys.solitaire.card.pile.rule.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoveFinder {
	public List<Move> getMovesFromPiles(Pile firstPile, Pile secondPile) {
		Set<Move> moves = new HashSet<>();

		// TODO this code generates a whole lot of duplicates, which is rather inelegantly handled by the Set we're using.

		if (firstPile.isTopOnly() || (secondPile.isTopOnly() && firstPile.getRule().canBuildOnTop())) {
			moves.addAll(topMove(firstPile, secondPile));
		} else {
			moves.addAll(iterate(firstPile, secondPile));
		}

		if (secondPile.isTopOnly() || (firstPile.isTopOnly() && secondPile.getRule().canBuildOnTop())) {
			moves.addAll(topMove(secondPile, firstPile));
		} else {
			moves.addAll(iterate(secondPile, firstPile));
		}

		return new ArrayList<>(moves);
	}

	List<Move> topMove(Pile firstPile, Pile secondPile) {
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

	Set<Move> iterate(Pile firstPile, Pile secondPile) {
		List<Card> firstPileCards = firstPile.getCards();
		Rule rule = secondPile.getRule();
		Set<Move> moves = new HashSet<>();

		for (int k = 0; k < firstPileCards.size(); k++) {
			Card firstCard = firstPileCards.get(k);
			Card secondCard = secondPile.peek();

			if (rule.eligible(secondCard, firstCard)) {
				Move move = new Move(firstPile, secondPile, firstCard);

				if (firstPile.getPileType() == PileType.TABLEAU && secondPile.getPileType().getRule().canBuildOnTop()) {
					List<Card> followers = firstPileCards.subList(k + 1, firstPileCards.size());
					move = move.withFollowers(followers);
				}

				moves.add(move);
			}
		}

		return moves;
	}
}