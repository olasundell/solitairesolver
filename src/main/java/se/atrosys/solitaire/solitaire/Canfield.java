package se.atrosys.solitaire.solitaire;

import se.atrosys.solitaire.cardstuff.Deck;
import se.atrosys.solitaire.cardstuff.EmptyDeckException;
import se.atrosys.solitaire.cardstuff.Move;
import se.atrosys.solitaire.cardstuff.piles.IneligibleCardException;
import se.atrosys.solitaire.cardstuff.piles.MoveFinder;
import se.atrosys.solitaire.cardstuff.piles.Pile;
import se.atrosys.solitaire.cardstuff.piles.pilerules.AlternatingColorDescendingRule;
import se.atrosys.solitaire.cardstuff.piles.pilerules.SameSuitAscendingAceFirstRule;

import java.util.ArrayList;
import java.util.List;

public class Canfield {
	private final MoveFinder moveFinder = new MoveFinder();
	private List<Pile> foundations;
	private List<Pile> tableau;
	private Pile reserve;
	private Pile stock;
	private Deck deck = new Deck();

	public Canfield() throws EmptyDeckException {
		setup();
	}

	protected void setup() throws EmptyDeckException {
		createPiles();
		dealCards();
	}

	private void createPiles() {
		foundations = new ArrayList<>();
		tableau = new ArrayList<>();
		reserve = new Pile();
		stock = new Pile();

		for (int i = 0 ; i < 4 ; i++) {
			foundations.add(new Pile().withRule(new SameSuitAscendingAceFirstRule()));
			tableau.add(new Pile().withRule(new AlternatingColorDescendingRule()));
		}
	}

	private void dealCards() throws EmptyDeckException {
		reserve.addCards(deck.takeSeveral(16));

		for (int i = 0 ; i < 4 ; i++) {
			try {
				tableau.get(i).addCard(deck.takeNext());
			} catch (EmptyDeckException | IneligibleCardException e) {
				// TODO use logger
				e.printStackTrace();
			}
		}

		stock.addCards(deck.takeRemaining());
	}

	public List<Move> getAvailableMoves() {
		List<Move> moves = new ArrayList<>();

		moves.addAll(getTableauMoves());

		return moves;
	}

	// TODO this method doesn't work very well if the pile list is empty.
	protected List<Move> getTableauMoves() {
		System.out.println(tableau);
		List<Move> moves = new ArrayList<>();

		for (int i = 0 ; i < tableau.size() ; i++)  {
			for (int j = i + 1 ; j < tableau.size() ; j++) {
				Pile firstPile = tableau.get(i);
				Pile secondPile = tableau.get(j);

				moves.addAll(moveFinder.getMovesFromPiles(firstPile, secondPile));
			}
		}

		return moves;
	}

	public List<Pile> getTableaus() {
		return tableau;
	}
}
