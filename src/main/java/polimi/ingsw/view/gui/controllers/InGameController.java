package polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import polimi.ingsw.model.*;
import polimi.ingsw.model.chat.Message;
import polimi.ingsw.model.enumeration.CardGoalType;
import polimi.ingsw.model.enumeration.Direction;
import polimi.ingsw.model.enumeration.TileType;
import polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import polimi.ingsw.model.interfaces.PlayerIC;
import polimi.ingsw.model.interfaces.TileIC;
import polimi.ingsw.view.gui.IntRecord;
import polimi.ingsw.view.gui.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * InGameController class.
 */
public class InGameController extends GenericController {

    @FXML
    private Pane tilesPane;

    @FXML
    private AnchorPane mainAnchor;


    @FXML
    private Label youNickname;
    @FXML
    private Label youPoints;
    @FXML
    private Pane youPersonal;
    @FXML
    private Pane youChair;

    @FXML
    private Label pgTilesTotal;
    @FXML
    private Label lableGameId;
    @FXML
    private Label labelMessage;
    @FXML
    private TextField messageText;
    @FXML
    private ListView chatList;
    @FXML
    private ListView importantEventsList;


    @FXML
    private ComboBox comboBoxMessage;


    @FXML
    private Label playerLabel1;
    @FXML
    private Label player1Points;
    @FXML
    private Pane pointGroup1;
    @FXML
    private Pane chair1;

    @FXML
    private Label playerLabel2;
    @FXML
    private Label player2Points;
    @FXML
    private Pane pointGroup2;
    @FXML
    private Pane chair2;

    @FXML
    private Label playerLabel3;
    @FXML
    private Label player3Points;
    @FXML
    private Pane pointGroup3;
    @FXML
    private Pane chair3;

    @FXML
    private Pane pointFinish;

    private boolean firstClick = true;
    private Integer rowFirstTile, colFirstTile, rowSecondTile, colSecondTile;
    private boolean needToDetectColSelection = false, needToDetectTileInHandGrabbing = false;


    /**
     * This method manages the click on a tile in the playground.
     *
     * @param e the mouse event
     */
    public void actionClickOnTile(MouseEvent e) {
        if (!needToDetectColSelection && !needToDetectTileInHandGrabbing) {
            if (e.getButton() == MouseButton.PRIMARY) {
                IntRecord rowCol = getRowColFrom(e, "pg");
                Integer row = rowCol.row();
                Integer col = rowCol.col();

                if (!firstClick) {
                    //Second click so client selected first and last Tile in the playground
                    rowSecondTile = row;
                    colSecondTile = col;
                    boolean isValid =checkAlignment(rowFirstTile, colFirstTile, rowSecondTile, colSecondTile);
                    if(isValid){
                        firstClick = !firstClick;
                        Sound.playSound("clickTile1.wav");
                    }else{
                        setMsgToShow("Tiles selection not valid",false);
                        rightClickMouse();
                    }


                } else {
                    rowFirstTile = row;
                    colFirstTile = col;
                    Sound.playSound("clickTile1.wav");
                    firstClick = !firstClick;
                }


            } else {
                rightClickMouse();
            }
        }
    }

    private void rightClickMouse(){
        this.rowFirstTile = null;
        this.colFirstTile = null;
        this.rowSecondTile = null;
        this.colSecondTile = null;
        firstClick = true;
        makeTilesNotSelectedExpectTheFirstOne();
        Sound.playSound("delete.wav");
    }


    /**
     * This method manages the click on a tile in the hand.
     *
     * @param e the mouse event
     */
    public void actionHandTileClick(MouseEvent e) {
        if (needToDetectTileInHandGrabbing) {
            Integer indexTileHandToPlace = getRowColFrom(e, "pgGrab").col();
            getInputReaderGUI().addTxt(indexTileHandToPlace.toString());
            Sound.playSound("placeTile.wav");
        }
    }

    /**
     * This method returns the row and the column of the tile clicked.
     *
     * @param e              the mouse event
     * @param prefixToRemove the prefix to remove
     * @return the row and the column of the tile clicked
     */
    private IntRecord getRowColFrom(MouseEvent e, String prefixToRemove) {
        final Node source = (Node) e.getSource();
        String id = source.getId();
        String rowCol = id.replaceAll(prefixToRemove, "");
        int row = Integer.parseInt(String.valueOf(rowCol.charAt(0)));
        int col = Integer.parseInt(String.valueOf(rowCol.charAt(1)));

        return new IntRecord(row, col);
    }

    /**
     * This method manage the mouse hover on a tile
     *
     * @param e
     */
    public void actionMouseEnteredTile(MouseEvent e) {
        if (rowFirstTile != null && colFirstTile != null && rowSecondTile == null && colSecondTile == null) {
            makeTilesNotSelectedExpectTheFirstOne();
            //I make visible the selected tiles until second click
            Pane tilePane;
            IntRecord destinPoint = getRowColFrom(e, "pg");
            List<IntRecord> points = getPointsBetween(rowFirstTile, colFirstTile, destinPoint.row(), destinPoint.col());
            for (IntRecord p : points) {
                tilePane = (Pane) tilesPane.lookup("#pg" + p.row() + p.col());
                if (!tilePane.getStyleClass().contains("selected")) {
                    tilePane.getStyleClass().add("selected");
                }

            }
        }
    }

    /**
     * This method manage the mouse hover on the playground
     *
     * @param e the mouse event
     */
    public void actionMouseEnteredPlayground(MouseEvent e) {
        makeTilesNotSelectedExpectTheFirstOne();
    }


    /**
     * This method manage the selection of the tiles
     */
    private void makeTilesNotSelectedExpectTheFirstOne() {
        String selector = ".selected";
        tilesPane.lookupAll(selector).forEach(element -> {
            element.getStyleClass().remove("selected");
        });

        if (rowFirstTile != null && colFirstTile != null) {
            ((Pane) tilesPane.lookup("#pg" + rowFirstTile + colFirstTile)).getStyleClass().add("selected");
        }
    }

    /**
     * This method manage the click on the shelfie
     *
     * @param e the mouse event
     */
    public void actionTileShelfieClick(MouseEvent e) {
        if (needToDetectColSelection && !needToDetectTileInHandGrabbing) {
            deselectAllCols();
            int colToPlaceTiles = getRowColFrom(e, "youShelf").col();
            needToDetectColSelection = false;

            needToDetectTileInHandGrabbing = true;
            changeCursorOnTilesPlayground(Cursor.DEFAULT);
            changeCursorOnTilesMyShelf(Cursor.DEFAULT);
            changeCursorOnInHandTiles(Cursor.HAND);

            getInputReaderGUI().addTxt(Integer.toString(colToPlaceTiles));
            Sound.playSound("clickTile1.wav");
        }
    }

    /**
     * This method manage the sending of the message
     *
     * @param e the mouse event
     */
    public void actionSendMessage(MouseEvent e) {
        if (!messageText.getText().isEmpty()) {
            Sound.playSound("sendmsg.wav");

            if (comboBoxMessage.getValue().toString().isEmpty()) {
                getInputReaderGUI().addTxt("/c " + messageText.getText());
            } else {
                //Player wants to send a private message
                getInputReaderGUI().addTxt("/cs " + comboBoxMessage.getValue().toString() + " " + messageText.getText());
                comboBoxMessage.getSelectionModel().selectFirst();
            }
            messageText.setText("");


        }
    }

    /**
     * This method manage the click on the button to send the message
     *
     * @param ke the key event
     */
    public void actionKeyPressedOnTextMessage(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            actionSendMessage(null);
        }
    }

    /**
     * This method manage the entering of the mouse on the shelf
     *
     * @param e the mouse event
     */
    public void actionMouseEntered(MouseEvent e) {
        if (needToDetectColSelection) {
            Pane tilePane;
            deselectAllCols();
            IntRecord rowCol = getRowColFrom(e, "youShelf");
            for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
                tilePane = (Pane) mainAnchor.lookup("#youShelf" + r + rowCol.col());
                tilePane.getStyleClass().add("selectedCols");
            }
        }
    }

    /**
     * This method manage the exiting of the mouse from the shelf
     *
     * @param e the mouse event
     */
    public void actionMouseExited(MouseEvent e) {
        deselectAllCols();
    }


    /**
     * This method manage the mouse entering on the common card
     *
     * @param e the mouse event
     */
    public void actionMouseEnteredCommonCard(MouseEvent e) {
        final Node source = (Node) e.getSource();
        String id = source.getId();

        switch (id) {
            case "cc0" -> {
                ((Group) mainAnchor.lookup("#groupPointCC0")).setVisible(false);
                ((Group) mainAnchor.lookup("#groupPointCC1")).setVisible(false);
                ((Pane) source).setMinWidth(490);
                ((Pane) source).setMinHeight(310);
            }
            case "cc1" -> {
                ((Group) mainAnchor.lookup("#groupPointCC1")).setVisible(false);
                ((Pane) source).setMinWidth(490);
                ((Pane) source).setMinHeight(310);
            }
            case "youPersonal" -> {
                ((Pane) source).setMinWidth(260);
                ((Pane) source).setMinHeight(400);
            }
        }
    }

    /**
     * This method manage the mouse exiting from the common card
     *
     * @param e the mouse event
     */
    public void actionMouseExitedCommonCard(MouseEvent e) {
        final Node source = (Node) e.getSource();
        String id = source.getId();


        switch (id) {
            case "cc0" -> {
                ((Group) mainAnchor.lookup("#groupPointCC0")).setVisible(true);
                ((Group) mainAnchor.lookup("#groupPointCC1")).setVisible(true);
                ((Pane) source).setMinWidth(190);
                ((Pane) source).setMinHeight(110);
            }
            case "cc1" -> {
                ((Group) mainAnchor.lookup("#groupPointCC1")).setVisible(true);
                ((Pane) source).setMinWidth(190);
                ((Pane) source).setMinHeight(110);
            }
            case "youPersonal" -> {
                ((Pane) source).setMinWidth(130);
                ((Pane) source).setMinHeight(200);
            }
        }
    }


    /**
     * This method deselect all the columns
     */
    private void deselectAllCols() {
        Pane tilePane;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                tilePane = (Pane) mainAnchor.lookup("#youShelf" + r + c);
                if (tilePane.getStyleClass().contains("selectedCols")) {
                    tilePane.getStyleClass().remove("selectedCols");
                }
            }
        }
    }


    /**
     * This method get the point between two tiles
     *
     * @param rowFirstTile  the row of the first tile
     * @param colFirstTile  the column of the first tile
     * @param rowSecondTile the row of the second tile
     * @param colSecondTile the column of the second tile
     * @return
     */
    private List<IntRecord> getPointsBetween(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile) {
        List<IntRecord> points = new ArrayList<>();

        if (rowFirstTile == rowSecondTile || colFirstTile == colSecondTile) {
            int minRow = Math.min(rowFirstTile, rowSecondTile);
            int maxRow = Math.max(rowFirstTile, rowSecondTile);
            int minCol = Math.min(colFirstTile, colSecondTile);
            int maxCol = Math.max(colFirstTile, colSecondTile);

            if (rowFirstTile == rowSecondTile) {
                for (int col = minCol; col <= maxCol; col++) {
                    points.add(new IntRecord(rowFirstTile, col));
                }
            } else {
                for (int row = minRow; row <= maxRow; row++) {
                    points.add(new IntRecord(row, colFirstTile));
                }
            }
        }

        return points;
    }


    /**
     * This method check if the two tiles are aligned
     *
     * @param rowFirstTile  the row of the first tile
     * @param colFirstTile  the column of the first tile
     * @param rowSecondTile the row of the second tile
     * @param colSecondTile the column of the second tile
     * @return true if grabbed tiles are valid
     */
    private boolean checkAlignment(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile) {
        Direction dir = null;
        Integer distance = null;

        if (rowFirstTile == rowSecondTile && Math.abs(colFirstTile - colSecondTile) < 3) {
            distance = Math.abs(colFirstTile - colSecondTile) + 1;

            if (distance != 1) {
                if (colFirstTile < colSecondTile) {
                    dir = Direction.RIGHT;
                } else {
                    dir = Direction.LEFT;
                }
            }
        } else if (colFirstTile == colSecondTile && Math.abs(rowFirstTile - rowSecondTile) < 3) {
            distance = Math.abs(rowFirstTile - rowSecondTile) + 1;

            if (rowFirstTile < rowSecondTile) {
                dir = Direction.DOWN;
            } else {
                dir = Direction.UP;
            }
        } else {
            return false;
        }


        getInputReaderGUI().addTxt(String.valueOf(distance));

        getInputReaderGUI().addTxt(String.valueOf(rowFirstTile));
        getInputReaderGUI().addTxt(String.valueOf(colFirstTile));

        if (distance != 1) {
            getInputReaderGUI().addTxt(dir.toString());
        }
        this.rowFirstTile = null;
        this.colFirstTile = null;
        this.rowSecondTile = null;
        this.colSecondTile = null;
        makeTilesNotSelectedExpectTheFirstOne();

        return true;
    }

    /**
     * Set the nickname and the points of the players
     *
     * @param model    the model of the game {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void setNicknamesAndPoints(GameModelImmutable model, String nickname) {
        youNickname.setTextFill(Color.WHITE);
        playerLabel1.setTextFill(Color.WHITE);
        playerLabel2.setTextFill(Color.WHITE);
        playerLabel3.setTextFill(Color.WHITE);
        Integer refToGui;
        Label labelNick = null, labelPoints = null;
        Pane chair=null;

        for (PlayerIC p : model.getPlayers()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());
            switch (refToGui) {
                case 0 -> {
                    labelNick = youNickname;
                    labelPoints = (Label) mainAnchor.lookup("#youPoints");
                    chair=youChair;
                }
                case 1 -> {
                    labelNick = playerLabel1;
                    labelPoints = (Label) mainAnchor.lookup("#player1Points");
                    chair=chair1;
                }
                case 2 -> {
                    labelNick = playerLabel2;
                    labelPoints = (Label) mainAnchor.lookup("#player2Points");
                    chair=chair2;
                }
                case 3 -> {
                    labelNick = playerLabel3;
                    labelPoints = (Label) mainAnchor.lookup("#player3Points");
                    chair=chair3;
                }
            }

            if(model.getFirstTurnIndex()==model.getPlayers().indexOf(model.getPlayerEntity(p.getNickname()))){
                chair.setVisible(true);
            }

            labelNick.setText(p.getNickname());
            labelNick.setVisible(true);

            labelPoints.setText(String.valueOf(p.getTotalPoints()));
            labelPoints.setVisible(true);

            if (model.getNicknameCurrentPlaying().equals(p.getNickname())) {
                labelNick.setTextFill(Color.YELLOW);
            }
        }

        //Set not visible to the points of the no-playing player
        Pane pointGroup = null;
        for (int i = model.getPlayers().size(); i < DefaultValue.MaxNumOfPlayer; i++) {
            switch (i) {
                case 2 -> {
                    pointGroup = pointGroup2;
                }
                case 3 -> {
                    pointGroup = pointGroup3;
                }
            }
            pointGroup.setVisible(false);
        }

        if (comboBoxMessage.getItems().size() == 0) {
            comboBoxMessage.getItems().add("");
            for (PlayerIC p : model.getPlayers()) {

                if (!p.getNickname().equals(nickname))
                    comboBoxMessage.getItems().add(p.getNickname());
            }
            comboBoxMessage.getSelectionModel().selectFirst();
        }

    }

    /**
     * This method return the index of the player
     *
     * @param model            the model of the game {@link GameModelImmutable}
     * @param personalNickname the nickname of the player
     * @param nickToGetRef     the nickname of the player to get the index
     * @return the index of the player in the GUI
     */
    private Integer getReferringPlayerIndex(GameModelImmutable model, String personalNickname, String nickToGetRef) {
        if (personalNickname.equals(nickToGetRef))
            return 0;

        int offset = 1;
        int playerNum = model.getPlayers().size();
        String otherNick;

        for (int i = 0; i < playerNum; i++) {
            otherNick = model.getPlayers().get(i).getNickname();

            if (!otherNick.equals(personalNickname)) {
                if (otherNick.equals(nickToGetRef)) {
                    return offset;
                }
                offset++;
            }

        }
        return null;
    }


    /**
     * This method set the playground
     *
     * @param model the model of the game {@link GameModelImmutable}
     */
    public void setPlayground(GameModelImmutable model) {
        TileIC t;
        Pane tilePane;
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                t = model.getPg().getTile_IC(r, c);

                tilePane = (Pane) tilesPane.lookup("#pg" + r + c);

                removeallBackgroundClass(tilePane);

                if (tilePane != null) {
                    if (!t.getType().equals(TileType.NOT_USED) && !t.getType().equals(TileType.USED) && !t.getType().equals(TileType.FINISHED_USING)) {
                        tilePane.getStyleClass().add(t.getBackground());
                        tilePane.getStyleClass().add("tileHover");
                        tilePane.setVisible(true);
                    } else {
                        tilePane.setVisible(false);
                    }
                }

            }
        }

        pgTilesTotal.setText(String.valueOf(model.getPg().getNumOfTileinTheBag()));
    }

    /**
     * This method remove all the background class of the tile
     *
     * @param tilePane the tile pane {@link Pane}
     */
    private void removeallBackgroundClass(Pane tilePane) {
        if (tilePane != null) {
            for (TileType t : TileType.values()) {
                for (int i = 0; i < 3; i++) {
                    if (tilePane.getStyleClass().contains(t.getBackgroundClass() + i)) {
                        tilePane.getStyleClass().remove(t.getBackgroundClass() + i);
                    }
                }
            }
        }
    }

    /**
     * This method set the personal card of the player
     *
     * @param model the model of the game {@link GameModelImmutable}
     */
    public void setCommonCards(GameModelImmutable model) {
        Pane tilePane;
        tilePane = (Pane) mainAnchor.lookup("#cc0");

        tilePane.getStyleClass().add(model.getCommonCards().get(0).getCommonType().getBackgroundClass());
        tilePane.setVisible(true);

        tilePane = (Pane) mainAnchor.lookup("#cc1");
        tilePane.getStyleClass().add(model.getCommonCards().get(1).getCommonType().getBackgroundClass());
        tilePane.setVisible(true);
    }


    /**
     * This method set the shelves of the player visible
     *
     * @param model the model of the game {@link GameModelImmutable}
     */
    public void setVisibleShelves(GameModelImmutable model) {
        Pane pane;

        setInvisibleAllShelves();

        int playerNum = model.getPlayers().size();
        for (int i = 1; i < playerNum; i++) {
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(true);

            //showPersonalCard(i+1,model.getPlayers().get(i).getSecretGoal().getGoalType().getBackgroundClass());
        }

        lableGameId.setText(model.getGameId().toString());
    }

    /**
     * This method set the shelves of the player invisible
     */
    private void setInvisibleAllShelves() {
        Pane pane;
        for (int i = 1; i <= DefaultValue.MaxNumOfPlayer - 1; i++) {
            pane = (Pane) mainAnchor.lookup("#workspace" + (i));
            pane.setVisible(false);

            pane = (Pane) mainAnchor.lookup("#chair" + (i));
            pane.setVisible(false);
        }

        youChair.setVisible(false);
    }


    /**
     * This method set the personal card of the player
     *
     * @param model    the model of the game {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void setPersonalCard(GameModelImmutable model, String nickname) {
        youPersonal.getStyleClass().add(model.getPlayerEntity(nickname).getSecretGoal_IC().getGoalType().getBackgroundClass());
    }

    /**
     * This method set the tiles of the player in the hand
     *
     * @param model    the model of the game {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void setHandTiles(GameModelImmutable model, String nickname) {
        float opacity = 0.5f;

        if (model.getNicknameCurrentPlaying().equals(nickname)) {
            opacity = 1;
        }

        Pane pane;
        int i = 0;
        setEmptyHand();
        for (TileIC t : model.getHandOfCurrentPlaying()) {
            pane = (Pane) mainAnchor.lookup("#pgGrab0" + i);
            pane.getStyleClass().remove(pane.getStyleClass().get(0));
            pane.getStyleClass().add(t.getBackground());
            pane.setOpacity(opacity);
            i++;
        }
    }

    /**
     * This method set the tiles of the player in the hand when the player has grabbed a tile
     *
     * @param model    the model of the game {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void setPlayerGrabbedTiles(GameModelImmutable model, String nickname) {
        setPlayground(model);
        this.rowFirstTile = null;
        this.colFirstTile = null;
        this.rowSecondTile = null;
        this.colSecondTile = null;
        makeTilesNotSelectedExpectTheFirstOne();
        setHandTiles(model, nickname);
    }

    /**
     * This method empty the hand of the player
     */
    private void setEmptyHand() {
        for (int i = 0; i < DefaultValue.maxNumOfGrabbableTiles; i++) {
            Pane pane = (Pane) mainAnchor.lookup("#pgGrab0" + i);
            pane.getStyleClass().remove(pane.getStyleClass().get(0));
            pane.getStyleClass().add("tileEmpty");
            pane.setOpacity(0.9);
            pane.setVisible(true);
        }
    }

    /**
     * This method set all the shelfies of the players
     *
     * @param model    the model of the game {@link GameModelImmutable}
     * @param nickname the nickname of the player
     */
    public void setAllShefies(GameModelImmutable model, String nickname) {
        String prefixIdPane = null;
        Integer refToGui;
        //setInvisibleAllShelfies();

        for (PlayerIC p : model.getPlayers()) {
            refToGui = getReferringPlayerIndex(model, nickname, p.getNickname());

            switch (refToGui) {
                case 0 -> {
                    prefixIdPane = "#youShelf";
                }
                case 1 -> {
                    prefixIdPane = "#player1Shelf";
                }
                case 2 -> {
                    prefixIdPane = "#player2Shelf";
                }
                case 3 -> {
                    prefixIdPane = "#player3Shelf";
                }
            }

            setShelfie(p.getShelf(), prefixIdPane);

        }
    }


    /**
     * This method set the shelf of the player
     *
     * @param shelf        the shelf of the player {@link Shelf}
     * @param prefixIdPane the prefix of the id of the pane
     */
    private void setShelfie(Shelf shelf, String prefixIdPane) {
        Pane paneTile;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                paneTile = (Pane) mainAnchor.lookup(prefixIdPane + r + c);
                if (!(shelf.getSingleTile(r, c).getType().equals(TileType.NOT_USED) || shelf.getSingleTile(r, c).getType().equals(TileType.FINISHED_USING))) {
                    paneTile.getStyleClass().add(shelf.getSingleTile(r, c).getBackground());
                }

            }
        }
    }

    /**
     * This method set the color of a message
     * @param msg the message to show
     * @param success if the message is a success or not
     */
    public void setMsgToShow(String msg, Boolean success) {
        labelMessage.setText(msg);
        if (success == null) {
            labelMessage.setTextFill(Color.WHITE);
        } else if (success) {
            labelMessage.setTextFill(Color.GREEN);
        } else {
            labelMessage.setTextFill(Color.RED);
        }
    }


    /**
     * This method manage the change of turn
     * @param model
     * @param nickname
     */
    public void changeTurn(GameModelImmutable model, String nickname) {
        needToDetectTileInHandGrabbing = false;

        setMsgToShow("Next turn is up to: " + model.getNicknameCurrentPlaying(), true);
        changeCursorOnTilesPlayground(Cursor.HAND);
        changeCursorOnTilesMyShelf(Cursor.DEFAULT);
        changeCursorOnInHandTiles(Cursor.DEFAULT);
    }


    /**
     * This method show the selected colunm of the shelfi
     */
    public void showSelectionColShelfie() {
        //Now the client can select a col from his shelfie to position all tiles
        needToDetectColSelection = true;
        needToDetectTileInHandGrabbing = false;
        changeCursorOnTilesPlayground(Cursor.DEFAULT);
        changeCursorOnTilesMyShelf(Cursor.HAND);
        changeCursorOnInHandTiles(Cursor.DEFAULT);
    }

    /**
     * Change the cursor on the tiles of the playground
     * @param cu the cursor to set
     */
    private void changeCursorOnTilesPlayground(Cursor cu) {
        Pane tilePane;
        for (int r = 0; r < DefaultValue.PlaygroundSize; r++) {
            for (int c = 0; c < DefaultValue.PlaygroundSize; c++) {
                tilePane = (Pane) tilesPane.lookup("#pg" + r + c);

                if (tilePane != null) {
                    tilePane.setCursor(cu);
                }

            }
        }
    }

    /**
     * Change the cursor on the tiles of the shelf
     * @param cu the cursor to set
     */
    private void changeCursorOnTilesMyShelf(Cursor cu) {
        Pane paneTile;
        for (int r = 0; r < DefaultValue.NumOfRowsShelf; r++) {
            for (int c = 0; c < DefaultValue.NumOfColumnsShelf; c++) {
                paneTile = (Pane) mainAnchor.lookup("#youShelf" + r + c);
                if (paneTile != null) {
                    paneTile.setCursor(cu);
                }
            }
        }
    }

    /**
     * Change the cursor on the tiles in hand
     * @param cu the cursor to set
     */
    private void changeCursorOnInHandTiles(Cursor cu) {
        Pane paneTile;
        paneTile = (Pane) mainAnchor.lookup("#pgGrab00");
        paneTile.setCursor(cu);

        paneTile = (Pane) mainAnchor.lookup("#pgGrab01");
        paneTile.setCursor(cu);

        paneTile = (Pane) mainAnchor.lookup("#pgGrab02");
        paneTile.setCursor(cu);

    }


    /**
     * This method set the message in the correct format
     * @param msgs the list of messages
     * @param myNickname the nickname of the player
     */
    public void setMessage(List<Message> msgs, String myNickname) {
        chatList.getItems().clear();
        for (Message m : msgs) {
            String r = "[" + m.getTime().getHour() + ":" + m.getTime().getMinute() + ":" + m.getTime().getSecond() + "] " + m.getSender().getNickname() + ": " + m.getText();

            if (m.whoIsReceiver().equals("*")) {
                chatList.getItems().add(r);
            } else if (m.whoIsReceiver().toUpperCase().equals(myNickname.toUpperCase()) || m.getSender().getNickname().toUpperCase().equals(myNickname.toUpperCase())) {
                //Message private
                chatList.getItems().add("[Private] " + r);
            }
        }
    }

    /**
     * This method set the important events
     * @param importantEvents the list of important events
     */
    public void setImportantEvents(List<String> importantEvents) {
        importantEventsList.getItems().clear();
        for (String s : importantEvents) {
            importantEventsList.getItems().add(s);
        }
        importantEventsList.scrollTo(importantEventsList.getItems().size());
    }

    /**
     * This method set the updated points
     * @param model the model of the game
     * @param playerPointChanged the player that has changed his points
     * @param myNickname the nickname of the player
     * @param p the point to update
     */
    public void setPointsUpdated(GameModelImmutable model, Player playerPointChanged, String myNickname, Point p) {
        setNicknamesAndPoints(model, myNickname);

        if (p.getReferredTo().equals(model.getCommonCards().get(0).getCommonType())) {
            //Point referred to First Common Card
            Pane paneTile = (Pane) mainAnchor.lookup("#point" + p.getPoint() + "cc0");
            paneTile.setVisible(false);
        } else if (p.getReferredTo().equals(model.getCommonCards().get(1).getCommonType())) {
            Pane paneTile = (Pane) mainAnchor.lookup("#point" + p.getPoint() + "cc1");
            paneTile.setVisible(false);
        }else if(p.isFinishedPoint()){
            this.pointFinish.setVisible(false);
        }

    }


    /**
     * Change the sound icon
     * @param e event
     */
    public void actionSound(MouseEvent e){
        if(e!=null) {
            Sound.play = !Sound.play;
        }
        Pane sound = (Pane)mainAnchor.lookup("#sound");
        if(Sound.play){
            mainAnchor.lookup("#sound").getStyleClass().remove("soundOFF");
            if(!sound.getStyleClass().contains("soundON")){
                sound.getStyleClass().add("soundON");
                Sound.playSound("clickmenu.wav");
            }
        }else{
            sound.getStyleClass().remove("soundON");
            if(!sound.getStyleClass().contains("soundOFF")){
                sound.getStyleClass().add("soundOFF");
            }
        }
    }
}
