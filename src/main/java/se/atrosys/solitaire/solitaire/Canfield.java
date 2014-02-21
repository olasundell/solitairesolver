package se.atrosys.solitaire.solitaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.cardstuff.Deck;
import se.atrosys.solitaire.cardstuff.EmptyDeckException;
import se.atrosys.solitaire.cardstuff.moves.Move;
import se.atrosys.solitaire.cardstuff.moves.MoveFinder;
import se.atrosys.solitaire.cardstuff.piles.Pile;
import se.atrosys.solitaire.cardstuff.piles.PileType;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.TakeOnlyRule;

import java.util.*;

// TODO create a builder for this class, it'll be quite heavy in due time.
public class Canfield {
	private final MoveFinder moveFinder = new MoveFinder();
	private Set<Pile> foundations;
	private List<Pile> tableaux;
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
		tableaux = new ArrayList<>();
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
	protected List<Move> getTableauInternalMoves() {
		List<Move> moves = new ArrayList<>();

		for (int i = 0 ; i < tableaux.size() ; i++)  {
			for (int j = i + 1 ; j < tableaux.size() ; j++) {
				Pile firstPile = tableaux.get(i);
				Pile secondPile = tableaux.get(j);

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

	public List<Pile> getTableaux() {
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

	public boolean isSolved() {
		for (Pile pile: foundations) {
			if (pile.peek().getRank() != 13) {
				return false;
			}
		}

		return true;
	}
}
