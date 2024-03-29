package polimi.ingsw.model.enumeration;

import polimi.ingsw.model.DefaultValue;

import java.util.List;
import java.util.Random;

/**
 * This enum represents the tile types
 */
public enum TileType {
    CAT("tileCat"),
    BOOK("tileBook"),
    ACTIVITY("tileActivity"),
    FRAME("tileFrame"),
    TROPHY("tileTrophy"),
    PLANT("tilePlant"),
    NOT_USED("tileNotused"),
    FINISHED_USING("tileFinishedusing"),
    USED("tileUsed");

    //for testing purposes
    private static final List<TileType> values = List.of(values());
    private static final Random rand = new Random();

    private final String backgroundClass;

    TileType(String backgroundClass) {
        this.backgroundClass = backgroundClass;
    }

    public String getBackgroundClass() {
        return backgroundClass;
    }

    public static TileType randomTile() {
        return values.get(rand.nextInt(DefaultValue.NumOfTileTypes));
    }

    //done like this so that the CAT tile is never chosen
    //also, so that the NOT_USED and FINISHED_USING are never chosen
    public static TileType randomTileCAT() {
        return values.get(rand.nextInt(DefaultValue.NumOfTileTypes - 1) + 1);
    }

    public static TileType randomTileCATeBOOK() {
        return values.get(rand.nextInt(DefaultValue.NumOfTileTypes - 2) + 2);
    }

    public static List<TileType> getValues() {
        return values;
    }

    /**
     * This method returns the TileType corresponding to the string passed as parameter
     * @return the TileType corresponding to the string passed as parameter
     */
    public static List<TileType> getUsableValues() {
        return values.stream()
                .filter(x -> (!(x.equals(TileType.USED) || x.equals(TileType.NOT_USED) || x.equals(TileType.FINISHED_USING)))).toList();
    }

}

