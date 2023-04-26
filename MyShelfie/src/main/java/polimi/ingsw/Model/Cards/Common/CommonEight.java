package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonEight extends CommonMethods {
    
    public CommonEight(CardCommonType type) {
        super(type);
    }

    @Override
    public boolean verify(Shelf toCheck) {
        Map<TileType, Integer> tileCheck = new HashMap<>();
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                if (!toCheck.get(i, j).isSameType(TileType.NOT_USED)) {
                    if (tileCheck.get(toCheck.get(i, j).getType()) == null) {
                        tileCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
                    } else {
                        tileCheck.put(toCheck.get(i, j).getType(), tileCheck.get(toCheck.get(i, j).getType()) + 1);
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
