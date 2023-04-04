package polimi.ingsw.View.RMI.remoteInterfaces;

import polimi.ingsw.Model.Enumeration.Direction;
import polimi.ingsw.Model.Enumeration.TileType;
import polimi.ingsw.Model.Exceptions.GameEndedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameControllerInterface extends Remote {
    boolean playerIsReadyToStart(String p) throws RemoteException;
    void grabTileFromPlayground(String p, int x, int y, Direction direction, int num) throws RemoteException;
    void positionTileOnShelf(String p, int column, TileType type) throws RemoteException, GameEndedException;
    boolean isThisMyTurn(String nick) throws RemoteException;
}
