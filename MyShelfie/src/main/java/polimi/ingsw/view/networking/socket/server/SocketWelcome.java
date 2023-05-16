package polimi.ingsw.view.networking.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketWelcome extends Thread {
    private ServerSocket serverSocket;
    private List<ClientHandler> handler;

    public void start(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            handler = new ArrayList<>();
            this.start();
            System.out.println("Server Socket ready");
        } catch (IOException e) {
            System.err.println("[ERROR] STARTING SOCKET SERVER: \n\tServer RMI exception: " + e);
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                handler.add(new ClientHandler(serverSocket.accept()));
                handler.get(handler.size() - 1).start();
                System.out.println("[SOCKET] new connection accepted");
            }
        } catch (IOException e) {
            System.err.println("[ERROR] ACCEPTING WELCOME SOCKET CONNECTION: \n\tServer SOCKET exception: " + e);
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopConnection() {
        for (ClientHandler c : handler) {
            c.interruptThread();
        }
        this.interrupt();
    }
}