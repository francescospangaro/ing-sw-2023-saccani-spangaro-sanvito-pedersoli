package polimi.ingsw.Model;

import polimi.ingsw.Model.Enumeration.TileType;

import static polimi.ingsw.Model.Enumeration.TileType.NOT_USED;

public class Tile {
    private TileType TYPE;
    private boolean freeSide;

    public Tile(){
        TYPE=NOT_USED;
        freeSide=false;
    }
    public Tile(TileType type){
        this.TYPE = type;
        freeSide=false;
    }

    public Tile(TileType TYPE, boolean freeSide) {
        this.TYPE = TYPE;
        this.freeSide = freeSide;
    }

    public TileType getType() {
        return TYPE;
    }

    public void setType(TileType TYPE) {
        this.TYPE = TYPE;
    }

    public boolean isFreeSide() {
        return freeSide;
    }

    public void setFreeSide(boolean freeSide) {
        this.freeSide = freeSide;
    }


    public boolean isSameType(TileType type){
        return this.TYPE==type;
    }
}
