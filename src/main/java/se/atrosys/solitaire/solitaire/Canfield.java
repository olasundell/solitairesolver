package se.atrosys.solitaire.solitaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.cardstuff.Deck;
import se.atrosys.solitaire.cardstuff.EmptyDeckException;
import se.atrosys.solitaire.cardstuff.moves.Move;
import se.atrosys.solitaire.cardstuff.piles.IneligibleCardException;
import se.atrosys.solitaire.cardstuff.moves.MoveFinder;
import se.atrosys.solitaire.cardstuff.piles.Pile;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;

import java.util.ArrayList;
import java.util.List;

public class Canfield {
	private final MoveFinder moveFinder = new MoveFinder();
	private List<Pile> foundations;
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
		foundations = new ArrayList<>();
		tableaux = new ArrayList<>();
		reserve = new Pile().withTakeOnly().withTopOnly();
		stock = new Pile().withTakeOnly();

		for (int i = 0 ; i < 4 ; i++) {
			foundations.add(new Pile().withRule(new SameSuitAscendingAceFirstRule()));
			tableaux.add(new Pile().withRule(new AlternatingColorDescendingRule()));
		}
	}

	private void dealCards() throws EmptyDeckException {
		reserve.addCards(deck.takeSeveral(16));

		for (int i = 0 ; i < 4 ; i++) {
			try {
				tableaux.get(i).addCard(deck.takeNext());
			} catch (EmptyDeckException | IneligibleCardException e) {
				logger.warn("Could not add card to tableaux", e);
			}
		}

		stock.addCards(deck.takeRemaining());
	}

	public List<Move> getAvailableMoves() {
		List<Move> moves = new ArrayList<>();

		moves.addAll(getTableauInternalMoves());
		moves.addAll(getTableauExternalMoves());

		return moves;
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
}
