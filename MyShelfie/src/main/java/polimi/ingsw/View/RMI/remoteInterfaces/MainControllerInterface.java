package polimi.ingsw.View.RMI.remoteInterfaces;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MainControllerInterface extends Remote {
    GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException;
    GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException;
    GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException;
}
