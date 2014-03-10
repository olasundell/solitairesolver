package se.atrosys.solitaire;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.atrosys.solitaire.card.move.IllegalMoveException;
import se.atrosys.solitaire.card.move.Move;
import se.atrosys.solitaire.card.pile.IneligibleCardException;
import se.atrosys.solitaire.game.Canfield;
import se.atrosys.solitaire.game.CanfieldBuilder;
import se.atrosys.solitaire.game.Solitaire;
import se.atrosys.solitaire.ntree.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Game {
	private final Map<String, Node> nodes = new HashMap<>();
	private final Set<Solitaire> solutions = new HashSet<>();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Game() {
	}

	void playGame(int randseed) throws SolitaireException {
		Canfield canfield = new CanfieldBuilder().setRandseed(randseed).createCanfield();
		Node<Canfield> node = new Node<>(canfield);
		nodes.put(node.getChecksum(), node);

		generateNode(canfield, node, 0);
	}

	boolean solutionFound() {
		return !solutions.isEmpty();
	}

	void generateNode(Canfield canfield, Node<Canfield> node, int count) throws IneligibleCardException, IllegalMoveException {
		if (count == 500) {
			logger.info("500");
			return;
		}

		// whittle away the obvious moves
		Set<Move> prioMoves = canfield.getPriorityMoves();

		while (prioMoves.size() > 0) {
			canfield.executeMove(prioMoves.iterator().next());
			prioMoves = canfield.getPriorityMoves();
		}

		Set<Move> availableMoves = canfield.getNormalMoves();
		for (Move move: availableMoves) {
			canfield.executeMove(move);
			Canfield copy = canfield.copy();

			if (nodes.containsKey(copy.hashString())) {
				canfield.undoLatest();
				continue;
			}

			Node<Canfield> childNode = node.addChild(copy);
			nodes.put(childNode.getChecksum(), childNode);
			canfield.undoLatest();

			if (copy.isSolved()) {
				logger.info("Found solution! {}", copy);
				solutions.add(copy);
				break;
			}
			generateNode(copy, childNode, count + 1);
		}
	}

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(Game.class);

		try {
			Game game = new Game();
			game.playGame(2);
			logger.info("Randseed {} had a tree with {} nodes", 2, game.nodes.size());
			if (game.solutionFound()) {
				logger.info("Found solution for randseed {}", 2);
			}
		} catch (SolitaireException e) {
			logger.error("Ooops", e);
		}
	}
}
