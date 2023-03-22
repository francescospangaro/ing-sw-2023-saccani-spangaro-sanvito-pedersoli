package polimi.ingsw.Model.Cards.Common;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.CardCommonType;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Shelf;
import polimi.ingsw.Model.Tile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommonDiagonal extends CardCommon {

    private final static Tile controller = new Tile(TileType.NOT_USED);
    private final int param;

    public CommonDiagonal(CardCommonType type, int param) {
        super(type);
        this.param = param;
    }

    @Override
    public boolean verify(Shelf toCheck) {
        int sum = 0;
        switch (param) {
            case(0): //5 of the same type tiles in diagonal (sx or dx)
                for (int i = 0; i < 2; i++) {
                    int check1 = 1;
                    int check2 = 1;
                    for (int k = i, j = 0; k < DefaultValue.NumOfRowsShelf - 1 && j < DefaultValue.NumOfColumnsShelf - 1; k++, j++) {     //controllo sx dx
                        if (toCheck.get(k,j) == controller || toCheck.get(k,j) !=toCheck.get(k+1,j+1) )
                            check1 = 0;
                    }
                    for (int k = i, j = DefaultValue.NumOfColumnsShelf - 1; k < DefaultValue.NumOfRowsShelf - 1 && j > 0; k++, j--) {     //controllo dx sx
                        if (toCheck.get(k,j) == controller || toCheck.get(k,j) != toCheck.get(k+1,j-1))
                            check2 = 0;
                    }
                    if (check1 == 1 || check2 == 1) {
                        return true;
                    }
                }
                return false;
            case(1): //tiles in ascending or descending order (like a triangle)
                int[] spaceCheck = {0,0,0,0,0};
                int checkSxToDx = 1;
                int checkDxToSx = 1;
                int checkSxToDx2 = 1;
                int checkDxToSx2 = 1;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    for (int i = 0; i < DefaultValue.NumOfRowsShelf; i++) {
                        if (toCheck.get(i,j) == controller)
                            spaceCheck[j]++;

                    }
                }
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkSxToDx = 0;
                    }
                    sum++;
                }
                sum--;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkDxToSx = 0;
                    }
                    sum--;
                }
                sum += 2;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkSxToDx2 = 0;
                    }
                    sum++;
                }
                sum--;
                for (int j = 0; j < DefaultValue.NumOfColumnsShelf; j++) {
                    if (spaceCheck[j] != sum) {
                        checkDxToSx2 = 0;
                    }
                    sum--;
                }
                return checkSxToDx != 0 || checkDxToSx != 0 || checkSxToDx2 != 0 || checkDxToSx2 != 0;
            default:
                System.out.println("Default error");
                return false;
        }
    }
}