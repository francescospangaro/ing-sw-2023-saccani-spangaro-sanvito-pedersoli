package polimi.ingsw.view.userView;

import polimi.ingsw.listener.GameListener;
import polimi.ingsw.model.gameModelView.GameModelImmutable;
import polimi.ingsw.model.Player;
import polimi.ingsw.view.userView.utilities.FileDisconnection;


public abstract class Flow implements GameListener {
    protected void resetGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (Player p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), -1);
        }
    }

    protected void saveGameId(FileDisconnection fileDisconnection, GameModelImmutable model) {
        for (Player p : model.getPlayers()) {
            fileDisconnection.setLastGameId(p.getNickname(), model.getGameId());
        }
    }


}