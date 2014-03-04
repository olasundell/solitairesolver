package se.atrosys.solitaire;

import se.atrosys.solitaire.game.Canfield;

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

    public static class Node {
        private String checksum;
        private final Canfield canfield;
        private final List<Node> nodes = new ArrayList<>();

        private Node(Canfield canfield) {
            this.canfield= canfield;
            checksum = canfield.hashString();
        }

        public String getChecksum() {
            return checksum;
        }

        public Canfield getGame() {
            return canfield;
        }

        public boolean isEmpty() {
            return nodes.isEmpty();
        }
    }
}
