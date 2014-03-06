package se.atrosys.solitaire.game;

import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.move.MoveFinder;
import se.atrosys.solitaire.card.move.PriorityMovePruner;
import se.atrosys.solitaire.card.pile.Pile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CanfieldMoveFinder {
	private final Canfield canfield;
	private final MoveFinder moveFinder = new MoveFinder();

	public CanfieldMoveFinder(Canfield canfield) {
		this.canfield = canfield;
	}

	public Set<Move> getPriorityMoves() {
		Set <Move> moves = getNormalMoves();

		int foundationMinimum = 13;
		for (Pile foundation: canfield.getFoundations()) {
			if (foundation.isEmpty()) {
				foundationMinimum = 0;
				// no need to iterate further
				break;
			} else if (foundationMinimum > foundation.peek().getRank()) {
				foundationMinimum = foundation.peek().getRank();
			}
		}

		return new PriorityMovePruner().pruneMoves(moves, foundationMinimum);
	}

	public Set<Move> getNormalMoves() {
		Set<Move> moves = new HashSet<>();

		// TODO if any of the tableaus are empty, then there can be only one move, which is reserve -> empty tableau
		for (Pile tableau: canfield.getTableaux()) {
			if (tableau.isEmpty() && !canfield.getReserve().isEmpty()) {
				moves.add(new Move(canfield.getReserve(), tableau, canfield.getReserve().peek()));
				return moves;
			}
		}

		moves.addAll(getTableauInternalMoves());
		moves.addAll(getTableauExternalMoves());
		moves.addAll(getReserveMoves());
		moves.addAll(getStockMoves());

		Set<Move> movesWithoutExecuted = new HashSet<>(moves);

		for (Move executed: canfield.getExecutedMoves()) {
			for (Move found: moves) {
				if (found.equals(executed)) {
					movesWithoutExecuted.remove(found);
				}
			}
		}

		return movesWithoutExecuted;
	}

	protected Set<Move> getTableauInternalMoves() {
		Set<Move> moves = new HashSet<Move>();
		Pile[] piles = canfield.getTableaux().toArray(new Pile[canfield.getTableaux().size()]);

		for (int i = 0; i < piles.length; i++) {
			for (int j = i + 1; j < piles.length; j++) {
				Pile firstPile = piles[i];
				Pile secondPile = piles[j];

				moves.addAll(moveFinder.getMovesFromPiles(firstPile, secondPile));
			}
		}

		return moves;
	}

	protected List<Move> getTableauExternalMoves() {
		List<Move> moves = new ArrayList<Move>();

		for (Pile tableau : canfield.getTableaux()) {
			for (Pile foundation : canfield.getFoundations()) {
				moves.addAll(moveFinder.getMovesFromPiles(tableau, foundation));
			}
		}

		return moves;
	}

	protected List<Move> getReserveMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Pile tableau : canfield.getTableaux()) {
			moves.addAll(moveFinder.getMovesFromPiles(canfield.getReserve(), tableau));
		}

		for (Pile foundation : canfield.getFoundations()) {
			moves.addAll(moveFinder.getMovesFromPiles(canfield.getReserve(), foundation));
		}

		return moves;
	}

	protected List<Move> getStockMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Pile tableau : canfield.getTableaux()) {
			moves.addAll(moveFinder.getMovesFromPiles(canfield.getStock(), tableau));
		}

		for (Pile foundation : canfield.getFoundations()) {
			moves.addAll(moveFinder.getMovesFromPiles(canfield.getStock(), foundation));
		}

		return moves;
	}
}