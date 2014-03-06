package se.atrosys.solitaire.card.move;

import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;

import java.util.*;

/**
 * "Prunes" a Set of moves so that only those with priority, ie simple no-brainer moves, are left - empty if none exists.
 */

public class PriorityMovePruner {
	public Set<Move> pruneMoves(Set<Move> rawMoves, int foundationMinimum) {
		Set<Move> pruned = new HashSet<>();

		for (Move move: rawMoves) {
			if (move.getTo().getPileType() == PileType.FOUNDATION) {
				pruned.addAll(pruneFoundation(move));
			}

			if (move.getFrom().getPileType() == PileType.RESERVE) {
				pruned.add(move);
			}
		}

		pruned = pruneAcesToFoundations(pruned);
		pruned = pruneByMinimumFoundationRank(pruned, foundationMinimum);

		return pruned;
	}

	public Set<Move> pruneByMinimumFoundationRank(Set<Move> moves, int foundationMinimum) {
		Set<Move> pruned = new HashSet<>();

		for (Move move: moves) {
			if (move.getTo().getPileType() == PileType.FOUNDATION) {
				if (move.getCard().getRank() < foundationMinimum + 3) {
					pruned.add(move);
				}
			} else {
				pruned.add(move);
			}
		}

		return pruned;
	}

	protected Set<Move> pruneAcesToFoundations(Set<Move> moves) {
		Set<Move> pruned = new HashSet<>();

		Set<Card> aces = new LinkedHashSet<>();
		Set<Pile> foundations = new LinkedHashSet<>();
		Set<Move> aceMoves = new LinkedHashSet<>();

		// first, find all relevant moves
		for (Move move: moves) {
			if (move.getTo().getPileType() == PileType.FOUNDATION && move.getCard().getRank() == 1) {
				if (!aces.contains(move.getCard())) {
					aces.add(move.getCard());
					aceMoves.add(new Move(move.getFrom(), null, move.getCard()));
				}
				foundations.add(move.getTo());
			} else {
				// pass through
				pruned.add(move);
			}
		}

		Move[] aceMoveArr = aceMoves.toArray(new Move[aceMoves.size()]);
		Pile[] foundationArr = foundations.toArray(new Pile[foundations.size()]);

		for (int i = 0 ; i < aceMoveArr.length ; i++) {
			pruned.add(new Move(aceMoveArr[i].getFrom(), foundationArr[i], aceMoveArr[i].getCard()));
		}

		return pruned;
	}

	protected Set<Move> pruneFoundation(Move move) {
		Set<Move> moves = new HashSet<>();

		if (move.getTo().getPileType() == PileType.FOUNDATION) {
			moves.add(move);
		}

		return moves;
	}
}
