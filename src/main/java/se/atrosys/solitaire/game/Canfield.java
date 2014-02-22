package se.atrosys.solitaire.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.Deck;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.move.MoveFinder;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;

import java.util.*;

// TODO create a builder for this class, it'll be quite heavy in due time.
public class Canfield {
	private final MoveFinder moveFinder = new MoveFinder();
	private Set<Pile> foundations;
	private Set<Pile> tableaux;
	private Pile reserve;
	private Pile stock;
	private Deck deck = new Deck();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public Canfield() throws EmptyDeckException {
		setup();
	}

	protected void setup() throws EmptyDeckException {
		createPiles();
		dealCards();
	}

	private void createPiles() {
		foundations = new HashSet<>();
		tableaux = new HashSet<>();
		reserve = new Pile(PileType.RESERVE).withName("reserve");
		stock = new Pile(PileType.STOCK).withName("stock");

		for (int i = 0 ; i < 4 ; i++) {
			foundations.add(new Pile(PileType.FOUNDATION).withName("foundation"+i));
			tableaux.add(new Pile(PileType.TABLEAU).withName("tableaux"+i));
		}
	}

	private void dealCards() throws EmptyDeckException {
		reserve.addCards(deck.takeSeveral(16));

//		for (int i = 0 ; i < 4 ; i++) {
		for (Pile tableau: tableaux) {
			try {
				tableau.dealCard(deck.takeNext());
			} catch (EmptyDeckException e) {
				logger.warn("Could not add card to tableaux", e);
			}
		}

		stock.dealCards(deck.takeRemaining());
	}

	public Set<Move> getAvailableMoves() {
		Set<Move> moves = new HashSet<>();

		// TODO if any of the tableaus are empty, then there can be only one move, which is reserve -> empty tableau
		for (Pile tableau: getTableaux()) {
			if (tableau.isEmpty() && !reserve.isEmpty()) {
				moves.add(new Move(reserve, tableau, reserve.peek()));
				return moves;
			}
		}

		moves.addAll(getTableauInternalMoves());
		moves.addAll(getTableauExternalMoves());
		moves.addAll(getReserveMoves());
		moves.addAll(getStockMoves());

		moves = pruneMovesToFoundations(moves);

		return moves;
	}

	protected Set<Move> pruneMovesToFoundations(Set<Move> moves) {
		Set<Move> prunedMoves = new HashSet<>();

		prunedMoves.addAll(moves);

		return prunedMoves;
	}

	// TODO this method doesn't work very well if the pile list is empty.
	protected Set<Move> getTableauInternalMoves() {
		Set<Move> moves = new HashSet<>();
		Pile[] piles = tableaux.toArray(new Pile[tableaux.size()]);

		for (int i = 0 ; i < piles.length ; i++)  {
			for (int j = i + 1 ; j < piles.length ; j++) {
				Pile firstPile = piles[i];
				Pile secondPile = piles[j];

				moves.addAll(moveFinder.getMovesFromPiles(firstPile, secondPile));
			}
		}

		return moves;
	}

	protected List<Move> getTableauExternalMoves() {
		List<Move> moves = new ArrayList<>();

		for (Pile tableau: getTableaux()) {
			for (Pile foundation: foundations) {
				moves.addAll(moveFinder.getMovesFromPiles(tableau, foundation));
			}
		}

		return moves;
	}

	public Set<Pile> getTableaux() {
		return tableaux;
	}

	protected List<Move> getReserveMoves() {
		ArrayList<Move> moves = new ArrayList<>();
		for (Pile tableau: getTableaux()) {
			moves.addAll(moveFinder.getMovesFromPiles(reserve, tableau));
		}

		for (Pile foundation: foundations) {
			moves.addAll(moveFinder.getMovesFromPiles(reserve, foundation));
		}

		return moves;
	}

	protected List<Move> getStockMoves() {
		ArrayList<Move> moves = new ArrayList<>();
		for (Pile tableau: getTableaux()) {
			moves.addAll(moveFinder.getMovesFromPiles(stock, tableau));
		}

		for (Pile foundation: foundations) {
			moves.addAll(moveFinder.getMovesFromPiles(stock, foundation));
		}

		return moves;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Canfield canfield = (Canfield) o;

		if (!foundations.equals(canfield.foundations)) return false;
		if (!reserve.equals(canfield.reserve)) return false;
		if (!stock.equals(canfield.stock)) return false;
		if (!tableaux.equals(canfield.tableaux)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = foundations.hashCode();
		result = 31 * result + tableaux.hashCode();
		result = 31 * result + reserve.hashCode();
		result = 31 * result + stock.hashCode();
		return result;
	}

	public boolean isSolved() {
		for (Pile pile: foundations) {
			if (pile.size() != 13 || pile.peek().getRank() != 13) {
				return false;
			}
		}

		return true;
	}

	protected Set<Pile> getFoundations() {
		return foundations;
	}

	protected Pile getStock() {
		return stock;
	}
}
