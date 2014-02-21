package se.atrosys.solitaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NTree {
    Node root;
    Map<Integer, Node> nodes;

    public NTree() {
        nodes = new HashMap<>();
    }

    public void add(Game game) {
        // TODO this needs some serious thought.
    }

    private static class Node {
        private final Integer checksum;
        private final Game game;
        private final List<Node> nodes = new ArrayList<>();

        private Node(Game game) {
            this.game = game;
            checksum = game.hashCode();
        }

        public Integer getChecksum() {
            return checksum;
        }

        public Game getGame() {
            return game;
        }

        public boolean isEmpty() {
            return nodes.isEmpty();
        }
    }
}
