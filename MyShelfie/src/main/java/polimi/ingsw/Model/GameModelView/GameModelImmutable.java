package polimi.ingsw.Model.GameModelView;

import polimi.ingsw.Model.*;
import polimi.ingsw.Model.Cards.Common.CommonCard;
import polimi.ingsw.Model.Chat.Chat;
import polimi.ingsw.Model.Enumeration.GameStatus;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class GameModelImmutable implements Serializable {
    private final List<Player> players;
    private final List<CommonCard> commonCards;
    private final Integer gameId;
    private final Playground pg;

    private final Integer currentPlaying;

    private final Chat chat;

    private final GameStatus status;

    private final Integer firstFinishedPlayer = -1;

    private final Integer indexWonPlayer = -1;


    public GameModelImmutable() {
        players = new ArrayList<>();
        commonCards = new ArrayList<>();
        gameId = -1;

        pg = new Playground();
        currentPlaying = -1;
        chat = new Chat();
        status = GameStatus.WAIT;
    }

    public GameModelImmutable(GameModel modelToCopy) {
        players = modelToCopy.getPlayers();
        commonCards = modelToCopy.getCommonCards();
        gameId = modelToCopy.getGameId();

        pg = modelToCopy.getPg();
        currentPlaying = modelToCopy.getCurrentPlaying();
        chat = modelToCopy.getChat();
        status = modelToCopy.getStatus();
    }

    public String getNicknameCurrentPlaying() {
        return players.get(currentPlaying).getNickname();
    }

    public List<Tile> getHandOfCurrentPlaying() {
        return players.get(currentPlaying).getInHandTile();
    }

    public Player getWinner() {
        if (indexWonPlayer != -1) {
            return players.get(indexWonPlayer);
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<CommonCard> getCommonCards() {
        return commonCards;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Playground getPg() {
        return pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public Chat getChat() {
        return chat;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Integer getFirstFinishedPlayer() {
        return firstFinishedPlayer;
    }

    public Integer getIndexWonPlayer() {
        return indexWonPlayer;
    }

    public Player getPlayerEntity(String playerNick) {
        return players.stream().filter(x -> x.getNickname().equals(playerNick)).toList().get(0);
    }

    public boolean isMyTurn(String nickname) {
        return players.get(currentPlaying).getNickname().equals(nickname);
    }

    public String toStringListPlayers() {
        String ris = "";
        int i = 1;
        for (Player p : players) {
            ris += "[#" + i + "]: " + p.getNickname() + "\n";
            i++;
        }
        return ris;
    }

    public Player getLastPlayer() {
        return players.get(players.size() - 1);
    }

    public CommonCard getLastCommonCard() {
        return commonCards.get(commonCards.size() - 1);
    }

    public Player getEntityCurrentPlaying() {
        return players.get(currentPlaying);
    }
}
