package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;
import polimi.ingsw.model.Tile;

public abstract class CommonMethods extends CommonCard {
    /**
     *
     * @param toCheck the player's shelf that needs checking
     * @return
     */
    @Override
    public abstract boolean verify(Shelf toCheck);

    /**
     * Constructor
     * @param type
     */
    public CommonMethods(CardCommonType type) {
        super(type);
    }

    /**
     *
     * @param toCopy
     * @return a copy to the param shelf
     */
    protected static Shelf getCopy(Shelf toCopy) {
        Shelf temp = new Shelf();
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                temp.setSingleTile(new Tile(toCopy.getSingleTile(r, c).getType()), r, c);
            }
        }
        temp.setFreeSpace(toCopy.getFreeSpace());
        return temp;
    }

    /**
     *
     * @param typeToCheck
     * @param temp
     * @param r
     * @param c
     * @return the number of tiles that form a contiguous group
     */
    public static int checkAdjacent(TileType typeToCheck, Shelf temp, int r, int c) {
        int res = 0, col, row = 0;
        boolean found = false;

        if (temp.getSingleTile(r, c).isSameType(typeToCheck)) {
            temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);
            res++;
        } else {
            while (row < DefaultValue.NumOfRowsShelf && !found) {
                col = 0;
                while (col < DefaultValue.NumOfColumnsShelf && !found) {
                    if (temp.getSingleTile(row, col).isSameType(typeToCheck)) {
                        temp.setSingleTile(new Tile(TileType.NOT_USED), row, col);
                        res++;
                        found = true;
                        r = row;
                        c = col;
                    }
                    col++;
                }
                row++;
            }
        }
        switch (r) {
            case (0) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, 0);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            case (DefaultValue.NumOfRowsShelf - 1) -> {
                switch (c) {
                    case (0) -> {
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        return res;
                    }
                }
            }
            default -> {
                switch (c) {
                    case (0) -> {
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    case (DefaultValue.NumOfColumnsShelf - 1) -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                    default -> {
                        if (temp.getSingleTile(r, c - 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c - 1);
                        }
                        if (temp.getSingleTile(r - 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r - 1, c);
                        }
                        if (temp.getSingleTile(r, c + 1).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r, c + 1);
                        }
                        if (temp.getSingleTile(r + 1, c).isSameType(typeToCheck)) {
                            res += checkAdjacent(typeToCheck, temp, r + 1, c);
                        }
                        return res;
                    }
                }
            }
        }
    }

    /**
     * Deletes all the adjacent tiles to one, of the same type
     * @param typeToCheck
     * @param temp
     * @param r
     * @param c
     */
    protected static void deleteAdjacent(TileType typeToCheck, Shelf temp, int r, int c) {

        if (r < 0 || r >= DefaultValue.NumOfRowsShelf || c >= DefaultValue.NumOfColumnsShelf ||
                c < 0 || !temp.getSingleTile(r, c).isSameType(typeToCheck) ||
            temp.isEmpty() || typeToCheck.equals(TileType.NOT_USED))
            return;
        temp.setSingleTile(new Tile(TileType.NOT_USED), r, c);

        //up
        deleteAdjacent(typeToCheck, temp, r - 1, c);
        //down
        deleteAdjacent(typeToCheck, temp, r + 1, c);
        //left
        deleteAdjacent(typeToCheck, temp, r, c - 1);
        //right
        deleteAdjacent(typeToCheck, temp, r, c + 1);
    }

}
