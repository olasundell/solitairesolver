package se.atrosys.solitaire.cardstuff;

import se.atrosys.solitaire.cardstuff.piles.Pile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Move {
	private final Pile from;
	private final Pile to;
	private final Card card;
	private final List<Card> followers;

	public Move(Pile from, Pile to, Card card) {
		this.from = from;
		this.to = to;
		this.card = card;
		followers = new ArrayList<>();
	}

	public Pile getFrom() {
		return from;
	}

	public Pile getTo() {
		return to;
	}

	public Card getCard() {
		return card;
	}

	public List<Card> getFollowers() {
		return followers;
	}

	public Move withFollowers(List<Card> followers) {
		this.followers.clear();
		this.followers.addAll(followers);

		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Move move = (Move) o;

		if (card != null ? !card.equals(move.card) : move.card != null) return false;
		if (followers != null ? !followers.equals(move.followers) : move.followers != null) return false;
		if (from != null ? !from.equals(move.from) : move.from != null) return false;
		if (to != null ? !to.equals(move.to) : move.to != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = from != null ? from.hashCode() : 0;
		result = 31 * result + (to != null ? to.hashCode() : 0);
		result = 31 * result + (card != null ? card.hashCode() : 0);
		result = 31 * result + (followers != null ? followers.hashCode() : 0);
		return result;
	}
}
