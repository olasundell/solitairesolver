package se.atrosys.solitaire.game;

import se.atrosys.solitaire.card.EmptyDeckException;

public class CanfieldBuilder {
	private long randseed;

	public CanfieldBuilder setRandseed(long randseed) {
		this.randseed = randseed;
		return this;
	}

	public Canfield createCanfield() throws EmptyDeckException {
		return new Canfield(randseed);
	}
}