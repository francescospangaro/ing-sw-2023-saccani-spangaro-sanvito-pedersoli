package polimi.ingsw.View.userView.text;

import polimi.ingsw.View.userView.ConnectionSelection;

public class mainTextUI_1 {
    public static void main(String[] args) {
        TextUI textUI = new TextUI(ConnectionSelection.RMI);
        textUI.start();
    }
}
