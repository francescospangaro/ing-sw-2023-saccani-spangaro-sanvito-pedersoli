package polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.exceptions.TileGrabbedNotCorrectException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlaygroundTest2 {
    Playground p;

    @BeforeEach
    void setUp() {
        p = new Playground(4);
        p.initialisePlayground();
    }

    @Test
    @DisplayName("Grab a tile")
    void testGrab() throws TileGrabbedNotCorrectException {
        assertThrows(TileGrabbedNotCorrectException.class, () -> {
            p.grabTile(0, 0, Direction.UP, 1);
        });
    }

    @Test
    @DisplayName("All tile have free side1")
    void testFreeSide1() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 4, 4);
        p.setSingleTile(TileType.TROPHY, 0, 0);
        p.setSingleTile(TileType.TROPHY, 0, 4);



        assert (p.allTileHaveAllFreeSide());
    }
    @Test
    @DisplayName("All tile have free side2")
    void testFreeSide2() {
        Playground p = new Playground(2);
        p.setEmptyPlayground();

        //random tiles that aren't touching
        p.setSingleTile(TileType.TROPHY, 3, 6);
        p.setSingleTile(TileType.TROPHY, 5, 1);
        p.setSingleTile(TileType.TROPHY, 7, 0);



        assert (p.allTileHaveAllFreeSide());
    }

}