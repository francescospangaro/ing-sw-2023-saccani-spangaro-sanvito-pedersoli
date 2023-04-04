package polimi.ingsw.Controller;

import polimi.ingsw.Listener.GameListener;
import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.Model.Enumeration.GameStatus;
import polimi.ingsw.Model.Exceptions.MaxPlayersInException;
import polimi.ingsw.Model.Exceptions.PlayerAlreadyInException;
import polimi.ingsw.Model.Player;
import polimi.ingsw.View.RMI.remoteInterfaces.GameControllerInterface;
import polimi.ingsw.View.RMI.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Gestisce tutte le partite in particolare la creazione, il join e il leave
public class MainController implements MainControllerInterface, Serializable {

    //Singleton
    private static MainController instance = null;

    private List<GameController> runningGames;


    private MainController(){
        runningGames = new ArrayList<GameController>();
    }

    public static MainController getInstance(){
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }




    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        Player p = new Player(nick);


        GameController c = new GameController();
        c.addListener(lis,p);
        runningGames.add(c);
        try {
            c.addPlayer(p);
        } catch (MaxPlayersInException | PlayerAlreadyInException e) {
            throw new RuntimeException(e);
        }

        return c;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {

        List<GameController> ris = runningGames.stream().filter(x->(x.getStatus().equals(GameStatus.WAIT) && x.getNumOfPlayers()<DefaultValue.MaxNumOfPlayer)).collect(Collectors.toList());
        Player p = new Player(nick);
        if(ris.size()>0){
            try {
                ris.get(0).addListener(lis,p);
                ris.get(0).addPlayer(p);
                return ris.get(0);
            }catch(MaxPlayersInException  | PlayerAlreadyInException e){
                ris.get(0).removeListener(lis,p);
            }
        }
        return null;

    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        List<GameController> ris = runningGames.stream().filter(x->(x.getId()==idGame)).toList();
        Player p = new Player(nick);

        if(ris.size()==1){
            try {
                ris.get(0).addListener(lis,p);
                ris.get(0).addPlayer(p);
                return ris.get(0);
            }catch(MaxPlayersInException  | PlayerAlreadyInException e){
                ris.get(0).removeListener(lis,p);
            }
        }
        return null;

    }


}
