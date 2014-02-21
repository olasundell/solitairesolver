package se.atrosys.solitaire;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NTreeTest {
    NTree nTree;
    @BeforeMethod
    public void setUp() throws Exception {
        nTree = new NTree();
    }

    @Test
    public void shouldBeAbleToAddNodes() {
        Game game = new Game();
        nTree.add(game);
    }
}
