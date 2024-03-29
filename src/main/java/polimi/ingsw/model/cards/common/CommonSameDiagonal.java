package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

public class CommonSameDiagonal extends CommonCard {
    /**
     * Constructor
     * @param type
     */
    public CommonSameDiagonal(CardCommonType type) {
        super(type);
    }
    /**
     * Check if the player's shelf met the same diagonal goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {
        boolean check1, check2;
        for (int i = 0; i < 2; i++) {
            check1 = true;
            check2 = true;

            //amazing for loop, checks for occurrences in diagonal going from top to bottom
            for (int r = i, c = 0; r < DefaultValue.NumOfRowsShelf - 2 + i && check1 && c < DefaultValue.NumOfColumnsShelf - 1; r++, c++) {
                if ((!toCheck.getSingleTile(r, c).isSameType(toCheck.getSingleTile(r + 1, c + 1).getType()) || toCheck.getSingleTile(r, c).isSameType(TileType.NOT_USED))) {
                    check1 = false;
                }
            }

            for (int r = DefaultValue.NumOfRowsShelf - 2 + i, c = 0; r > i && c < DefaultValue.NumOfColumnsShelf; r--, c++) {
                if (!toCheck.getSingleTile(r, c).isSameType(toCheck.getSingleTile(r - 1, c + 1).getType()) || toCheck.getSingleTile(r, c).isSameType(TileType.NOT_USED)) {
                    check2 = false;
                }
            }

            if (check1 || check2)
                return true;
        }
        return false;
    }
}
