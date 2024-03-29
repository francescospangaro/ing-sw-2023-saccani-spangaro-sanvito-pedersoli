package polimi.ingsw.model.cards.common;

import polimi.ingsw.model.DefaultValue;
import polimi.ingsw.model.enumeration.CardCommonType;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonEight extends CommonMethods {
    /**
     * Constructor
     *
     * @param type
     */
    public CommonEight(CardCommonType type) {
        super(type);
    }

    /**
     * Check if the player's shelf met the common eight goal
     *
     * @return true if the goal is satisfied, false else
     */
    @Override
    public boolean verify(Shelf toCheck) {

        Map<TileType, Integer> tileCheck = new HashMap<>();

        if (toCheck.getOccupiedSpace() < 8)
            return false;

        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (!toCheck.getSingleTile(i, j).isSameType(TileType.NOT_USED)) {
                    if (tileCheck.get(toCheck.getSingleTile(i, j).getType()) == null) {
                        tileCheck.putIfAbsent(toCheck.getSingleTile(i, j).getType(), 1);
                    } else {
                        tileCheck.put(toCheck.getSingleTile(i, j).getType(), tileCheck.get(toCheck.getSingleTile(i, j).getType()) + 1);
                    }
                }
            }
        }
        for (TileType t : TileType.values()) {
            if (Optional.ofNullable(tileCheck.get(t)).orElse(0) >= 8) {
                return true;
            }
        }
        return false;
    }
}
