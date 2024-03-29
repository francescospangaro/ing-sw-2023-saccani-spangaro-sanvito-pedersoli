package polimi.ingsw.model;

import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.CardType;
import polimi.ingsw.model.interfaces.PointIC;

import java.io.Serializable;

/**
 * Point class
 * Each card has a list of points that can only be assigned once to each player
 * this class implements the Point object
 */
public class Point implements Serializable, PointIC {
    private Integer point;
    private CardType referredTo;

    private boolean isFinishedPoint=false;

    /**
     * Constructor referred to a card @param referredTo
     *
     * @param point
     * @param referredTo
     */
    public Point(Integer point, CardType referredTo) {
        this.point = point;
        this.referredTo = referredTo;
    }

    /**
     * Constructor default not referred to any card
     *
     * @param point
     */
    public Point(Integer point) {
        this.point = point;
        this.referredTo = CardGoalType.NOT_SET;
    }

    /**
     * Constructor for finished point
     *
     * @param isFinishedPoint boolean for finished
     */
    public Point(boolean isFinishedPoint){
        this.isFinishedPoint=isFinishedPoint;
        if(isFinishedPoint){
            point=1;
            referredTo=CardGoalType.NOT_SET;
        }
    }

    /**
     * @return the point obj
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point init point
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return the card type this point id referred to
     */
    public CardType getReferredTo() {
        return referredTo;
    }

    /**
     * @param referredTo sets the point to a said card type
     */
    public void setReferredTo(CardGoalType referredTo) {
        this.referredTo = referredTo;
    }

    /**
     * @return isFinishedPoint
     */
    public boolean isFinishedPoint(){
        return isFinishedPoint;
    }

}
