package se.atrosys.solitaire.solitaire;

import se.atrosys.solitaire.cardstuff.Deck;
import se.atrosys.solitaire.cardstuff.EmptyDeckException;
import se.atrosys.solitaire.cardstuff.Move;
import se.atrosys.solitaire.cardstuff.piles.Pile;

import java.util.ArrayList;
import java.util.List;

public class Canfield {
	private final List<Pile> foundations;
	private final List<Pile> tableau;
	private final Pile reserve;
	private final Pile stock;
	private Deck deck = new Deck();

	public Canfield() throws EmptyDeckException {

		foundations = new ArrayList<>();
		tableau = new ArrayList<>();
		reserve = new Pile();
		stock = new Pile();

		for (int i = 0 ; i < 4 ; i++) {
			foundations.add(new Pile());
			tableau.add(new Pile());
		}

		reserve.addCards(deck.getSeveral(16));

		for (int i = 0 ; i < 4 ; i++) {
			try {
				tableau.get(i).addCard(deck.getNext());
			} catch (EmptyDeckException e) {
				// TODO use logger
				e.printStackTrace();
			}
		}
	}

	public List<Move> getAvailableMoves() {
		List<Move> moves = new ArrayList<>();

		moves.addAll(getTableauMoves());

		return moves;
	}

	protected void setDeck(Deck deck) {

	}

	protected List<Move> getTableauMoves() {
		System.out.println(tableau);
		List<Move> moves = new ArrayList<>();

		return moves;
	}
}
