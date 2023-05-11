package polimi.ingsw.Model;

import polimi.ingsw.Model.Enumeration.CardGoalType;
import polimi.ingsw.Model.Enumeration.CardType;

import java.io.Serializable;

public class Point implements Serializable {
    private Integer point;
    private CardType referredTo;

    public Point(Integer point, CardType referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    public Point(Integer point){
        this.point = point;
        this.referredTo = null;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public CardType getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(CardGoalType referredTo) {
        this.referredTo = referredTo;
    }

}
