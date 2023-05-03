package polimi.ingsw.View.userView.gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateANewGameSceneController {
    private String nickName;
    @FXML
    private TextField nickNameTextField;

    private SceneController sceneController = new SceneController();
    public void EnterNickName(ActionEvent e) throws IOException {
        if (nickNameTextField.getText().length() > 0) {
            nickName = nickNameTextField.getText();
            System.out.println("Nickname :" + nickName);
            //For testing
            sceneController.switchToLobbyScene(e);
        } else {
            //Generate an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You have to enter a nickname");
            alert.setContentText("Please enter a nickname");
            alert.showAndWait();
        }
    }
}
