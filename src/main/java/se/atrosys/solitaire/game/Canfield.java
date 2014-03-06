package se.atrosys.solitaire.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.Card;
import se.atrosys.solitaire.card.Deck;
import se.atrosys.solitaire.card.EmptyDeckException;
import se.atrosys.solitaire.card.move.IllegalMoveException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.move.MoveFinder;
import se.atrosys.solitaire.card.pile.IneligibleCardException;
import se.atrosys.solitaire.card.pile.Pile;
import se.atrosys.solitaire.card.pile.PileType;
import se.atrosys.solitaire.card.pile.rule.PileComparator;

import java.util.*;

// TODO create a builder for this class, it'll be quite heavy in due time - job started, needs to be finished.
public class Canfield implements Solitaire {
	private final MoveFinder moveFinder = new MoveFinder();
	private final CanfieldMoveFinder canfieldMoveFinder = new CanfieldMoveFinder(this);
	private final MoveAsserter moveAsserter = new MoveAsserter(this);
	private Deque<Pile> foundations;
	private Deque<Pile> tableaux;
	private Pile reserve;
	private Pile stock;
	private Deck deck = new Deck();
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Queue<Move> executedMoves = new ArrayDeque<>();

	protected Canfield(long randseed) throws EmptyDeckException {
		deck = new Deck(randseed);
		setup();
	}

	private Canfield() { createPiles(); }

	protected void setup() throws EmptyDeckException {
		createPiles();
		dealCards();
	}

	private void createPiles() {
		foundations = new ArrayDeque<>();
		tableaux = new ArrayDeque<>();
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

	public Set<Move> getPriorityMoves() {
		return canfieldMoveFinder.getPriorityMoves();
	}

	public Set<Move> getNormalMoves() {
		return canfieldMoveFinder.getNormalMoves();
	}

	protected Set<Move> getTableauInternalMoves() {
		return canfieldMoveFinder.getTableauInternalMoves();
	}

	public Deque<Pile> getTableaux() {
		return tableaux;
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
		// this means that we have a completely solvable solitaire.
		if (reserve.size() == 1) {
			return true;
		}

		for (Pile pile: foundations) {
			if (pile.size() != 13 || pile.peek().getRank() != 13) {
				return false;
			}
		}

		return true;
	}

	protected Deque<Pile> getFoundations() {
		return foundations;
	}

	protected Pile getStock() {
		return stock;
	}

	@Override
	public String hashString() {
		StringBuilder builder = new StringBuilder();

		java.util.SortedSet<Pile> sortedFoundations = new TreeSet<>(new PileComparator());
		java.util.SortedSet<Pile> sortedTableaux = new TreeSet<>(new PileComparator());

		sortedFoundations.addAll(foundations);
		sortedTableaux.addAll(tableaux);

		for (Pile pile: sortedFoundations) {
			builder.append('F');
			builder.append(pile.toCardString());
		}

		for (Pile pile: sortedTableaux) {
			builder.append('T');
			builder.append(pile.toCardString());
		}

		builder.append('R');
		builder.append(reserve.toCardString());

		builder.append('S');
		builder.append(stock.toCardString());

		return builder.toString();
	}

	@Override
	public void executeMove(Move move) throws IneligibleCardException, IllegalMoveException {
		moveAsserter.assertMove(move);

		Pile from = move.getFrom();
		Pile to = move.getTo();
		from.removeCard(move.getCard());
		to.addCard(move.getCard());

		for (Card follower: move.getFollowers()) {
			from.removeCard(follower);
			to.addCard(follower);
		}

		executedMoves.add(move);
	}

	// TODO move this to a separate delegated class
	protected void assertMove(Move move) throws IllegalMoveException {

		moveAsserter.assertMove(move);
	}

	/**
	 * Will undo the latest move, or silently do nothing if no moves have been made.
	 * @throws IneligibleCardException
	 */

	public void undoLatest() throws IneligibleCardException {
		// TODO rewrite this in a way more robust manner
		if (executedMoves.isEmpty()) {
			return;
		}

		Move move = executedMoves.poll();
		Pile from = move.getFrom();
		Pile to = move.getTo();

		from.dealCard(move.getCard());
		to.removeCard(move.getCard());

		for (Card follower: move.getFollowers()) {
			from.dealCard(follower);
			to.removeCard(follower);
		}
	}

	public Canfield copy() {
		Canfield canfield = new Canfield();

		canfield.tableaux.clear();
		for (Pile pile: tableaux) {
			canfield.tableaux.add(pile.copy());
		}

		canfield.foundations.clear();
		for (Pile pile: foundations) {
			canfield.foundations.add(pile.copy());
		}

		canfield.stock = this.stock.copy();
		canfield.reserve = this.reserve.copy();

		canfield.executedMoves.addAll(executedMoves);

		return canfield;
	}

	protected Queue<Move> getExecutedMoves() {
		Queue<Move> ret = new ArrayDeque<>(executedMoves);
		return ret;
	}

	public Pile getReserve() {
		return reserve;
	}
}
