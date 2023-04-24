package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommonHorizontalCard extends CommonCard {
    private static int param;
    //private static int numOfTiles;
    //private static int numOfRows;
    public CommonHorizontalCard(CardCommonType type, int param) {
        super(type);
        CommonHorizontalCard.param = param;
        //CommonHorizontalCard.numOfTiles = numOfTiles;
        //CommonHorizontalCard.numOfRows = numOfRows;
    }

    /**
     * Check if the player's shelf met the horizontal goals
     *
     * @return true if the goal is satisfied, false else
     */
    /*@Override
    public boolean verify(Shelf toCheck){
        int sum=0;
        for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
            int ok = check(toCheck, i);
            if(numOfTiles==5){
                if (ok == numOfTiles)
                    sum++;
            }
            else {
                if (ok <= numOfTiles)
                    sum++;
            }
            if (sum == numOfRows) {
                return true;
            }
        }
        return false;
    }*/

    @Override
    public boolean verify(Shelf toCheck) {
        param = super.getCommonType().compareTo(CardCommonType.CommonHorizontal0) > 0 ? 1 : 0;
        int sum = 0;
        switch (param) {
            case (0) -> {       //4 lines with up to 3 different types of tiles
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
                System.out.println("Default error");
                return false;
            }
        }
    }

    private int check(Shelf toCheck, int i) {
        Map<TileType, Integer> rowCheck = new HashMap<>();
        int ok = 0;
        int count = 0;
        for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
            if (!toCheck.get(i, j).isSameType(TileType.NOT_USED))
                rowCheck.putIfAbsent(toCheck.get(i, j).getType(), 1);
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
