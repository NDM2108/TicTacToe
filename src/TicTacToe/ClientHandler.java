package TicTacToe;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    final int id;
    final Server server;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;

    public ClientHandler(int id, Server server, Socket s, DataInputStream dis, DataOutputStream dos) {
        this.id = id;
        this.server = server;
        this.socket = s;
        this.dataInputStream = dis;
        this.dataOutputStream = dos;
    }

    @Override
    public void run() {

        while (true) {
            try {
                String received;
                received = dataInputStream.readUTF();
                server.handler(id, received);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) throws IOException {
        dataOutputStream.writeUTF(message);
    }
}
