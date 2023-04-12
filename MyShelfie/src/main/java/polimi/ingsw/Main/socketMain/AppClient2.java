package polimi.ingsw.Main.socketMain;

import polimi.ingsw.Model.DefaultValue;
import polimi.ingsw.View.socket.client.ClientSocket;
import polimi.ingsw.View.socket.server.SocketWelcome;
import polimi.ingsw.View.userView.ConnectionSelection;
import polimi.ingsw.View.userView.View;
import polimi.ingsw.View.userView.text.TextUI;

import java.io.IOException;

public class AppClient2 {
    private static ClientSocket client2;

    public static void main(String[] args) throws IOException {
        View gui = new TextUI(ConnectionSelection.SOCKET);
        client2 = new ClientSocket(gui);

    }
}
