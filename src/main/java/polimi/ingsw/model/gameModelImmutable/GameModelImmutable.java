package polimi.ingsw.model.gameModelImmutable;

import polimi.ingsw.model.*;
import polimi.ingsw.model.cards.common.CommonCard;
import polimi.ingsw.model.chat.Chat;
import polimi.ingsw.model.enumeration.GameStatus;
import polimi.ingsw.model.interfaces.*;

import java.io.Serializable;
import java.util.*;

public class GameModelImmutable implements Serializable {
    private final List<PlayerIC> players;
    private final List<CommonCardIC> commonCards;
    private final Integer gameId;
    private final PlaygroundIC pg;

    private final Integer currentPlaying;

    private final ChatIC chat;

    private final GameStatus status;

    private final Integer firstFinishedPlayer = -1;

    private final Integer indexWonPlayer = -1;
    private Map<Integer, Integer> leaderBoard;


    public GameModelImmutable() {
        players = new ArrayList<>();
        commonCards = new ArrayList<>();
        gameId = -1;

        pg = new Playground();
        leaderBoard = new HashMap<>();
        currentPlaying = -1;
        chat = new Chat();
        status = GameStatus.WAIT;
    }

    public GameModelImmutable(GameModel modelToCopy) {
        players = new ArrayList<PlayerIC>(modelToCopy.getPlayers());
        commonCards = new ArrayList<CommonCardIC>(modelToCopy.getCommonCards());
        gameId = modelToCopy.getGameId();

        pg = modelToCopy.getPg();
        currentPlaying = modelToCopy.getCurrentPlaying();
        chat = modelToCopy.getChat();
        status = modelToCopy.getStatus();
        leaderBoard = modelToCopy.getLeaderBoard();
    }

    public String getNicknameCurrentPlaying() {
        return players.get(currentPlaying).getNickname();
    }

    public List<TileIC> getHandOfCurrentPlaying() {
        return players.get(currentPlaying).getInHandTile_IC();
    }

    public PlayerIC getWinner() {
        if (indexWonPlayer != -1) {
            return players.get(indexWonPlayer);
        }
        return null;
    }

    public List<PlayerIC> getPlayers() {
        return players;
    }

    public List<PlayerIC> getScoreboard(){
        players.sort(Comparator.comparing(p -> p.getTotalPoints(),Comparator.reverseOrder()));
        return players;
    }


    public List<CommonCardIC> getCommonCards() {
        return commonCards;
    }

    public Integer getGameId() {
        return gameId;
    }

    public PlaygroundIC getPg() {
        return pg;
    }

    public Integer getCurrentPlaying() {
        return currentPlaying;
    }

    public ChatIC getChat() {
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

    public PlayerIC getPlayerEntity(String playerNick) {
        return players.stream().filter(x -> x.getNickname().equals(playerNick)).toList().get(0);
    }

    public Map<Integer, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public boolean isMyTurn(String nickname) {
        return players.get(currentPlaying).getNickname().equals(nickname);
    }

    public String toStringListPlayers() {
        String ris = "";
        int i = 1;
        for (PlayerIC p : players) {
            ris += "[#" + i + "]: " + p.getNickname() + "\n";
            i++;
        }
        return ris;
    }

    public PlayerIC getLastPlayer() {
        return players.get(players.size() - 1);
    }

    public CommonCardIC getLastCommonCard() {
        return commonCards.get(commonCards.size() - 1);
    }

    public PlayerIC getEntityCurrentPlaying() {
        return players.get(currentPlaying);
    }
}