package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static polimi.ingsw.networking.PrintAsync.printAsync;


public class CommonHorizontalCard extends CommonCard {
    private static int param;

    /**
     * Constructor
     * @param type
     * @param param
     */
    public CommonHorizontalCard(CardCommonType type, int param) {
        super(type);
        CommonHorizontalCard.param = param;
    }

    /**
     * Check if the player's shelf met the horizontal goals
     *
     * @return true if the goal is satisfied, false if not
     */
    @Override
    public boolean verify(Shelf toCheck) {
        param = super.getCommonType().compareTo(CardCommonType.CommonHorizontal0) > 0 ? 1 : 0;
        int sum = 0;
        switch (param) {
            case (0) -> {       //4 lines with up to 3 different types of tiles
                if (toCheck.getOccupiedSpace() < 20)
                    return false;
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    int ok = check(toCheck, i);
                    if (ok <= 3)
                        sum++;
                    if (sum == 4) {
                        return true;
                    }
                }
                return false;
            }
            case (1) -> {       //2 rows of 5 different types of tiles
                if (toCheck.getOccupiedSpace() < 10)
                    return false;
                for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                    int ok = check(toCheck, i);
                    if (ok == 5)
                        sum++;
                    if (sum == 2) {
                        return true;
                    }
                }
                return false;
            }
            default -> {
                printAsync("Default error");
                return false;
            }
        }
    }

    /**
     * Checks the shelf
     * @param toCheck
     * @param i
     * @return
     */
    private int check(Shelf toCheck, int i) {
        Map<TileType, Integer> rowCheck = new HashMap<>();
        int ok = 0;
        int count = 0;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (!toCheck.getSingleTile(i, j).isSameType(TileType.NOT_USED))
                rowCheck.putIfAbsent(toCheck.getSingleTile(i, j).getType(), 1);
            else
                count++;
        }
        for (TileType t : TileType.values()) {
            ok = ok + Optional.
                    ofNullable(rowCheck.get(t)).
                    orElse(0);
        }
        if (count > 0)
            ok = 4;
        return ok;
    }
}
